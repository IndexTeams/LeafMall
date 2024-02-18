package cn.kiroe.mall.promo.controller;

import cn.kiroe.mall.common.constant.RedisConst;
import cn.kiroe.mall.common.constant.ResultCodeEnum;
import cn.kiroe.mall.common.result.Result;
import cn.kiroe.mall.common.util.AuthContext;
import cn.kiroe.mall.common.util.DateUtil;
import cn.kiroe.mall.common.util.MD5;
import cn.kiroe.mall.mq.constant.MqTopicConst;
import cn.kiroe.mall.mq.producer.BaseProducer;
import cn.kiroe.mall.order.dto.OrderDetailDTO;
import cn.kiroe.mall.order.dto.OrderTradeDTO;
import cn.kiroe.mall.order.query.OrderInfoParam;
import cn.kiroe.mall.promo.api.dto.SeckillGoodsDTO;
import cn.kiroe.mall.promo.client.OrderApiClient;
import cn.kiroe.mall.promo.client.UserApiClient;
import cn.kiroe.mall.promo.constant.StockStatus;
import cn.kiroe.mall.promo.converter.SeckillGoodsConverter;
import cn.kiroe.mall.promo.model.OrderRecord;
import cn.kiroe.mall.promo.model.SeckillGoods;
import cn.kiroe.mall.promo.model.UserRecord;
import cn.kiroe.mall.promo.service.PromoService;
import cn.kiroe.mall.promo.util.LocalCacheHelper;
import cn.kiroe.mall.user.dto.UserAddressDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @Author Kiro
 * @Date 2024/02/05 16:00
 **/
@RestController
@RequiredArgsConstructor
public class PromoController {
    private final PromoService promoService;
    private final BaseProducer baseProducer;
    private final RedissonClient redissonClient;
    private final UserApiClient userApiClient;
    private final SeckillGoodsConverter seckillGoodsConverter;
    private final OrderApiClient orderApiClient;
    private static String getEncrypt(final Long skuId, final String userId) {
        return MD5.encrypt(userId + skuId);
    }

    /**
     * 获取秒杀商品列表
     *
     * @return
     */
    @GetMapping("/seckill")
    public Result findAll() {
        // 查询Redis中所有的缓存商品
        return Result.ok(promoService.findAll());
    }

    @GetMapping("seckill/{skuId}")
    public Result getSeckillGoods(@PathVariable("skuId") Long skuId) {
        // 获取秒杀商品数据详情
        return Result.ok(promoService.getSeckillGoodsDTO(skuId));
    }

    /**
     * 获取下单码
     * 作用： 防止非法请求来请求我们的秒杀接口，用来保护秒杀下单接口
     * 下单码的工作流程：
     * a. 在抢购之前，先获取下单码，返回给前端
     * b. 前端发起秒杀下单请求并且携带下单码
     * c. 后端在收到请求后，校验请求中的写代码是否正确，如果不正确，说明是非法请求，直接拒绝
     * 下单码如何生成：
     * 1. redis: 使用uuid字符串，然后以userId作为key,uuid作为value,然后uuid作为下单码给前端
     * 2. 加密 | HASH: 验证下单码是否正确
     *
     * @param skuId
     * @param request
     * @return
     */
    @GetMapping("seckill/auth/getSeckillSkuIdStr/{skuId}")
    public Result getSeckillSkuIdStr(@PathVariable("skuId") Long skuId, HttpServletRequest request) {

        // 获取用户id
        String userId = AuthContext.getUserId(request);

        // 获取秒杀商品信息
        SeckillGoodsDTO seckillGoods = promoService.getSeckillGoodsDTO(skuId);
        if (null != seckillGoods) {
            Date curTime = new Date();
            // 判断当前时间是否在秒杀商品开始时间和结束时间之内
            if (DateUtil.dateCompare(seckillGoods.getStartTime(), curTime)
                    && DateUtil.dateCompare(curTime, seckillGoods.getEndTime())) {
                // 生成下单码
                // 可以用其他方式生成
                String skuIdStr = getEncrypt(skuId, userId);
                return Result.ok(skuIdStr);
            }
        }
        return Result.fail().message("获取下单码失败");
    }

    /**
     * 秒杀排队
     *
     * @param skuId
     * @param skuIdStr
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping("/seckill/auth/seckillOrder/{skuId}")
    public Result seckillOrder(@PathVariable("skuId") Long skuId, String skuIdStr, HttpServletRequest request) throws Exception {
        String userId = AuthContext.getUserId(request);
        // 1. 校验下单码（下单码生成规则可以自定义）
        if (!getEncrypt(skuId, userId).equals(skuIdStr)) {
            return Result.fail("下单码不对");
        }
        // 2. 校验状态位
        if (LocalCacheHelper.get(String.valueOf(skuId)).equals(StockStatus.NO_STOCK)) {
            return Result.fail("没有库存");
        }
        // 3. 将秒杀用户加入队列
        UserRecord userRecord = new UserRecord();
        userRecord.setUserId(userId);
        userRecord.setSkuId(skuId);
        // 发送消息到秒杀队列
        return baseProducer.sendMessage(MqTopicConst.SECKILL_GOODS_QUEUE_TOPIC, userRecord)
                ? Result.ok() : Result.fail("排队失败");
    }

    /**
     * 6. 前端轮询检测消息的排队情况
     * 检测的结果有四种
     * 1. 排队中
     * 2. 抢购失败
     * 3. 抢购成功，去下单
     * 4. 下单成功，已经生成了订单
     *
     * @param skuId
     * @param
     * @return
     */
    @GetMapping("/seckill/auth/checkOrder/{skuId}")
    public Result checkOrder(@PathVariable Long skuId,HttpServletRequest request) {
        String userId = AuthContext.getUserId(request);
        //0. 检查是否已经提交了秒杀订单
        RMap<Object, Object> submitOrderMap = redissonClient.getMap(RedisConst.PROMO_SUBMIT_ORDER);
        if (submitOrderMap.containsKey(userId)){
            return Result.build(null,ResultCodeEnum.SECKILL_ORDER_SUCCESS);
        }
        // 1. 判断用户是否已经下过单
        // 检查redis中是否有临时订单记录
        RBucket<Long> bucket = redissonClient.getBucket(RedisConst.PROMO_USER_ORDERED_FLAG + userId);
        Long skuIdInRedis = bucket.get();
        if (skuIdInRedis != null) {
            // 2. 判断Redis中是否已经存在用户的订单
            // 如果有返回值
            boolean isHasKey = redissonClient.getMap(RedisConst.PROMO_SECKILL_ORDERS).containsKey(userId);
            if (isHasKey) {
                // 抢单成功
                OrderRecord OrderRecord = (OrderRecord) redissonClient.getMap(RedisConst.PROMO_SECKILL_ORDERS).get(userId);
                // 秒杀成功！
                return Result.build(OrderRecord, ResultCodeEnum.SECKILL_SUCCESS);
            }
        }

        // 3. 判断库存状态位,这个必须放在后面，因为可能已经抢到了
        Object state = LocalCacheHelper.get(skuId.toString());
        if (StockStatus.NO_STOCK.equals(state)) {
            // 已售罄 抢单失败
            return Result.build(null, ResultCodeEnum.SECKILL_FAIL);
        }

        // 正在排队中
        return Result.build(null, ResultCodeEnum.SECKILL_RUN);
    }

    @GetMapping("/seckill/auth/trade")
    public Result trade(HttpServletRequest request) {
        // 1. 获取到用户Id
        String userId = AuthContext.getUserId(request);
        // 2. 获取用户的收获地址信息
        List<UserAddressDTO> userAddressListByUserId = userApiClient.findUserAddressListByUserId(userId);
        // 3. 获取订单中的商品, 从redis中取出获取这个用户的临时订单记录
        OrderRecord orderRecord = (OrderRecord) redissonClient.getMap(RedisConst.PROMO_SECKILL_ORDERS).get(userId);
        SeckillGoods seckillGoods = orderRecord.getSeckillGoods();
        List<OrderDetailDTO> orderDetailDTOList = Collections.singletonList(seckillGoodsConverter.secondKillGoodsToOrderDetailDTO(seckillGoods, 1));
        // 返回
        OrderTradeDTO orderTradeDTO = OrderTradeDTO.builder().userAddressList(userAddressListByUserId)
                                           .detailArrayList(orderDetailDTOList)
                                           .totalNum(orderRecord.getNum())
                                           .totalAmount(seckillGoods.getCostPrice().multiply(new BigDecimal(orderRecord.getNum()))).build();
        return Result.ok(orderTradeDTO);
    }

    /**
     * 8. 提交秒杀订单
     * 远程调用
     * @param orderInfoParam
     * @param request
     * @return
     */
    @PostMapping("/seckill/auth/submitOrder")
    public Result submitOrder(@RequestBody OrderInfoParam orderInfoParam, HttpServletRequest request) {
        String userId = AuthContext.getUserId(request);
        // 防止重复提交，判断redisson
        RMap<Object, Object> submittingMap = redissonClient.getMap(RedisConst.PROMO_SUBMITTING);
        // hsetnx
        Object object = submittingMap.putIfAbsent(userId, orderInfoParam.getOrderDetailList().get(0).getSkuId());
        if (object != null){
            return Result.fail().message("正在提交上一个订单，请稍后重试");
        }
        orderInfoParam.setUserId(Long.valueOf(userId));
        // 提交秒杀订单,把秒杀订单保存至数据库中
        Long orderId = orderApiClient.submitOrder(orderInfoParam);
        // 提交订单完成后，将redis中临时订单记录删掉，然后新增一个提交

        //删除Redis中 下单信息（OrderRecode）
        redissonClient.getMap(RedisConst.PROMO_SECKILL_ORDERS).remove(userId);
        //增加Redis中秒杀提交订单标记，表示用户已经提交了订单
        redissonClient.getMap(RedisConst.PROMO_SUBMIT_ORDER).put(userId,orderId.toString());
        return Result.ok(orderId);
    }
}
