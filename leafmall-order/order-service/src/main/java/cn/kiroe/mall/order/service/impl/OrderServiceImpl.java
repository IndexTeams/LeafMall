package cn.kiroe.mall.order.service.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.log.Log;
import cn.kiroe.mall.cart.api.dto.CartInfoDTO;
import cn.kiroe.mall.common.constant.ResultCodeEnum;
import cn.kiroe.mall.common.result.Result;
import cn.kiroe.mall.mq.constant.MqTopicConst;
import cn.kiroe.mall.order.client.CartApiClient;
import cn.kiroe.mall.order.client.ProductApiClient;
import cn.kiroe.mall.order.client.UserApiClient;
import cn.kiroe.mall.order.client.WareApiClient;
import cn.kiroe.mall.order.constant.OrderStatus;
import cn.kiroe.mall.order.converter.CartInfoConverter;
import cn.kiroe.mall.order.converter.OrderDetailConverter;
import cn.kiroe.mall.order.converter.OrderInfoConverter;
import cn.kiroe.mall.order.dto.OrderDetailDTO;
import cn.kiroe.mall.order.dto.OrderInfoDTO;
import cn.kiroe.mall.order.dto.OrderTradeDTO;
import cn.kiroe.mall.order.mapper.OrderDetailMapper;
import cn.kiroe.mall.order.mapper.OrderInfoMapper;
import cn.kiroe.mall.order.model.OrderDetail;
import cn.kiroe.mall.order.model.OrderInfo;
import cn.kiroe.mall.order.mq.producer.OrderProducer;
import cn.kiroe.mall.order.query.OrderDetailParam;
import cn.kiroe.mall.order.query.OrderInfoParam;
import cn.kiroe.mall.order.service.OrderService;
import cn.kiroe.mall.user.dto.UserAddressDTO;
import cn.kiroe.mall.ware.api.constant.TaskStatus;
import cn.kiroe.mall.ware.api.dto.WareOrderTaskDTO;
import cn.kiroe.mall.ware.api.dto.WareSkuDTO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author Kiro
 * @Date 2024/02/01 10:17
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final UserApiClient userApiClient;
    private final CartApiClient cartApiClient;
    private final CartInfoConverter cartInfoConverter;
    private final OrderInfoMapper orderInfoMapper;
    private final OrderDetailMapper orderDetailMapper;
    private final ProductApiClient productApiClient;
    private final WareApiClient wareApiClient;
    private final OrderInfoConverter orderInfoConverter;
    private final OrderProducer orderProducer;
    private final OrderDetailConverter orderDetailConverter;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long submitOrder(final OrderInfoParam orderInfoParam, final Long userId) {
        // 1. 校验商品价格是否产生变动
        // 1.1. 如果发生变动,下单失败 将购物车中的 价格更新为 当前的时候价格
        List<OrderDetailParam> orderDetailList = orderInfoParam.getOrderDetailList();
        if (!checkAndRefreshAllPrice(userId, orderDetailList)) {
            log.info("购物车价格改变");
            throw new RuntimeException("购物车价格改变");
        }
        // 1.2 价格一致
        // 2. 校验库存
        // 2.1 大多数电商网站，下单时不会减扣库存的，在支付后再减库存
        // 直接调用 库存服务
        if (!checkStock(orderDetailList)) {
            log.info("库存不足");
            throw new RuntimeException("库存不足");
        }
        // 下单,转换
        OrderInfo orderInfo = orderInfoConverter.convertOrderInfoParam(orderInfoParam);
        // 设置值
        orderInfo.setOrderStatus(OrderStatus.UNPAID.name());
        orderInfo.setUserId(userId);
        orderInfo.sumTotalAmount(); // 分别设置了两个值
        orderInfo.setOutTradeNo(UUID.fastUUID().toString().replace("-", ""));
        orderInfo.setTradeBody(orderDetailList.stream().map(OrderDetailParam::getSkuName).toList().toString());
        Date expireTime = new Date(System.currentTimeMillis() + MqTopicConst.DELAY_ORDER_LEVEL_TIME);
        orderInfo.setExpireTime(expireTime);
        log.info("开始保存订单");
        // 保存订单
        Long orderId = saveOrderInfo(orderInfo);
        // 3. 删除购物车中对应的商品
        List<Long> skuIdList = orderInfo.getOrderDetailList().stream().map(OrderDetail::getSkuId).toList();
        Result result = cartApiClient.removeCartProductsInOrder(userId.toString(), skuIdList);
        if (!ResultCodeEnum.SUCCESS.getCode().equals(result.getCode())) {
            throw new RuntimeException("删除失败");
        }

        return orderId;
    }

    private Boolean checkStock(final List<OrderDetailParam> orderDetailList) {
        for (final OrderDetailParam orderDetailParam : orderDetailList) {
            Long skuId = orderDetailParam.getSkuId();
            Integer skuNum = orderDetailParam.getSkuNum();
            Result result = wareApiClient.hasStock(skuId, skuNum);
            if (!ResultCodeEnum.SUCCESS.getCode().equals(result.getCode())) {
                return false;
            }
        }
        return true;
    }

    private Boolean checkAndRefreshAllPrice(final Long userId, final List<OrderDetailParam> orderDetailList) {
        for (final OrderDetailParam orderDetailParam : orderDetailList) {
            Long skuId = orderDetailParam.getSkuId();
            BigDecimal orderPrice = orderDetailParam.getOrderPrice();// 现在的价格
            // 获取当前最新的价格
            if (!checkPrice(skuId, orderPrice)) {
                refreshPrice(skuId, userId.toString());
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean checkPrice(final Long skuId, final BigDecimal skuPrice) {
        BigDecimal nowSkuPrice = productApiClient.getSkuPrice(skuId);
        return skuPrice.equals(nowSkuPrice);
    }

    @Override
    public void refreshPrice(final Long skuId, final String userId) {
        cartApiClient.refreshCartPrice(userId, skuId);
    }

    @Override
    public Long saveOrderInfo(final OrderInfo orderInfo) {
        log.info("开始保存订单");
        // 1. 保存订单
        int inserted = orderInfoMapper.insert(orderInfo);
        if (inserted != 1) {
            throw new RuntimeException("订单插入失败");
        }
        Long orderId = orderInfo.getId();
        // 2. 保存订单明细
        List<OrderDetail> orderDetailList = orderInfo.getOrderDetailList();
        for (final OrderDetail orderDetail : orderDetailList) {
            orderDetail.setOrderId(orderId);
        }
        boolean saveBatch = Db.saveBatch(orderDetailList);
        if (!saveBatch) {
            throw new RuntimeException("订单详情插入失败");
        }
        log.info("保存订单成功");
        log.info("开始发送消息");
        // 3. 发送一个订单超时取消的消息，就在订单服务消费，发送订单id
        // 消费者 检测订单的状态，如果为未支付，修改为订单状态为已取消
        if (orderInfo.getParentOrderId() == null) {// 子订单不用发生
            orderProducer.setOrderTimeOut(orderId);
        }
        return orderId;
    }

    @Override
    public OrderInfoDTO getOrderInfo(final Long orderId) {
        return orderInfoMapper.selectOneById(orderId);
    }

    /**
     * // 获取用户订单的分页列表
     * // 注意：
     * // 1. 建议使用MybatisPlus的分页查询功能
     * // 2. 思考一下哪些订单状态不用在用户页面展示出来？
     * // 3. 查询结果需要设置OrderInfoDTO中的orderStatusName(如'未支付'| '已支付' | '已发货')
     *
     * @param pageParam
     * @param userId
     * @return
     */
    @Override
    public IPage<OrderInfoDTO> getPage(final Page<OrderInfoDTO> pageParam, final String userId) {
        // 通过mapper获取，并筛选掉不显示的 订单状态
        // 最后再设置OrderInfoDTO
        // 获取Orderinfo带detail的信息
        // 首先获取orderInfo
        // 然后获取详细信息
        Page<OrderInfo> orderInfoPage = new Page<>();
        orderInfoPage.setSize(pageParam.getSize());
        orderInfoPage.setCurrent(pageParam.getCurrent());
        orderInfoPage.addOrder(new OrderItem().setAsc(false).setColumn("id"));
        orderInfoMapper.selectPage(orderInfoPage, new LambdaQueryWrapper<OrderInfo>()
                .eq(OrderInfo::getUserId, Long.parseLong(userId))
                .notIn(OrderInfo::getOrderStatus,OrderStatus.SPLIT.name(),OrderStatus.STOCK_EXCEPTION));
        // 获取detail
        // 填入info
        for (final OrderInfo orderInfo : orderInfoPage.getRecords()) {
            List<OrderDetail> orderDetails = orderDetailMapper.selectList(new LambdaQueryWrapper<OrderDetail>()
                    .eq(OrderDetail::getOrderId, orderInfo.getId()));
            orderInfo.setOrderDetailList(orderDetails);
        }
        // 复制至 PageOrderDetail
        pageParam.setRecords(orderInfoConverter.POListtoDTOList(orderInfoPage.getRecords()));
        pageParam.setTotal(orderInfoPage.getTotal());
       // 填入page
        for (final OrderInfoDTO orderInfoDTO : pageParam.getRecords()) {
            orderInfoDTO.setOrderStatusName(OrderStatus.getStatusDescByStatus(orderInfoDTO.getOrderStatus()));
        }
        return pageParam;
    }


    /**
     * 1. 更新订单状态为 已支付
     * 2. 远程调用仓储服务的接口,扣减库存
     * 在扣减库存的过程中，仓储服务，判断是否拆单，不同仓库的 拆分成不同订单
     * 3. 拆单之后，仓储服务会调用
     *
     * @param orderId
     */
    @Override
    @Transactional
    public void successPay(final Long orderId) {

        // int update = orderInfoMapper.update(new LambdaUpdateWrapper<OrderInfo>()
        //         .set(OrderInfo::getOrderStatus, OrderStatus.PAID.name())
        //         .eq(OrderInfo::getId, orderId));
        // if (update != 1) {
        //     throw new RuntimeException("更新订单状态异常");
        // }
        wareApiClient.decreaseStock(orderId);// 扣减库存，如果需要拆单,会调用订单的拆单接口，以及订单明细，更具不同的仓库id进行拆单


    }

    @Override
    public void successLockStock(final String orderId, final String taskStatus) {
        // 判断是否扣减库存,更改状态为 待发货,子也要修改
        // 直接通过orderId修改
        if (TaskStatus.DEDUCTED.name().equals(taskStatus)) {


            // 更行子订单
            int update = orderInfoMapper.update(new LambdaUpdateWrapper<OrderInfo>()
                    .set(OrderInfo::getOrderStatus, OrderStatus.WAIT_DELEVER)
                    .eq(OrderInfo::getParentOrderId, Long.parseLong(orderId)));
            String  parentOrderStatus = update==0?OrderStatus.WAIT_DELEVER.name():OrderStatus.SPLIT.name();
            // 更新当前订单
            orderInfoMapper.update(new LambdaUpdateWrapper<OrderInfo>()
                    .set(OrderInfo::getOrderStatus, parentOrderStatus)
                    .eq(OrderInfo::getId, Long.parseLong(orderId)));


        }

    }

    /**
     * 拆单接口
     *
     * @param orderId
     * @param wareSkuDTOList
     * @return
     */
    @Override
    public List<WareOrderTaskDTO> orderSplit(final String orderId, final List<WareSkuDTO> wareSkuDTOList) {
        ArrayList<WareOrderTaskDTO> wareOrderTaskDTOS = new ArrayList<>();
        // 1. 保存子订单以及子订单明细至数据库
        OrderInfoDTO originOrderInfo = getOrderInfo(Long.valueOf(orderId));
        // 使用detailMap
        Map<Long, OrderDetailDTO> detailDTOMap = originOrderInfo.getOrderDetailList().stream().collect(Collectors.toMap(OrderDetailDTO::getSkuId, detail -> detail));
        // 拆分子订单,通过 wareSkuDTOList
        for (final WareSkuDTO wareSkuDTO : wareSkuDTOList) {
            OrderInfo subOrderInfo = getSubOrderInfo(orderId, wareSkuDTO, originOrderInfo, detailDTOMap);
            // 保存子订单以及子订单明细
            saveOrderInfo(subOrderInfo);
            // 将几个放入 库存信息中
            WareOrderTaskDTO wareOrderTaskDTO = orderInfoConverter.convertOrderInfoToWareOrderTaskDTO(subOrderInfo);
            wareOrderTaskDTOS.add(wareOrderTaskDTO);
        }
        // 2. 修改原订单状态为【已拆分】
        orderInfoMapper.update(new LambdaUpdateWrapper<OrderInfo>()
                .set(OrderInfo::getOrderStatus, OrderStatus.SPLIT.name())
                .eq(OrderInfo::getId, orderId));
        return wareOrderTaskDTOS;
    }

    private OrderInfo getSubOrderInfo(final String orderId, final WareSkuDTO wareSkuDTO, final OrderInfoDTO originOrderInfo, final Map<Long, OrderDetailDTO> detailDTOMap) {
        // 创建子订单
        OrderInfo subOrderInfo = orderInfoConverter.copyOrderInfo(originOrderInfo);
        // 给成员变量赋值
        // 商品明细
        ArrayList<OrderDetail> orderDetailList = new ArrayList<>();
        for (final String skuId : wareSkuDTO.getSkuIds()) {
            OrderDetailDTO orderDetailDTO = detailDTOMap.get(Long.valueOf(skuId));
            orderDetailDTO.setId(null);
            orderDetailDTO.setCreateTime(new Date());
            orderDetailDTO.setUpdateTime(new Date());
            orderDetailList.add(orderDetailConverter.convertOrderDetailDTO2PO(orderDetailDTO));
        }
        subOrderInfo.setOrderDetailList(orderDetailList);
        // 保存子订单以及子订单明细
        subOrderInfo.setId(null);
        subOrderInfo.setOrderStatus(OrderStatus.PAID.name());
        subOrderInfo.sumTotalAmount();
        subOrderInfo.setWareId(wareSkuDTO.getWareId());
        subOrderInfo.setParentOrderId(Long.valueOf(orderId));
        subOrderInfo.setOutTradeNo(UUID.fastUUID().toString().replace("-", ""));
        subOrderInfo.setTradeBody(orderDetailList.stream().map(OrderDetail::getSkuName).toList().toString());
        return subOrderInfo;
    }

    /**
     * 将订单状态变为 已取消
     *
     * @param orderId
     */
    @Override
    public void execExpiredOrder(final Long orderId) {
        OrderInfo orderInfo = orderInfoMapper.selectById(orderId);
        orderInfo.setOrderStatus(OrderStatus.CLOSED.name());
        orderInfoMapper.updateById(orderInfo);
    }

    @Override
    public Long saveSeckillOrder(final OrderInfoParam orderInfoParam) {
        return null;
    }

    /**
     * 获取订单确认页面信息
     *
     * @param userId
     * @return
     */
    @Override
    public OrderTradeDTO trade(final String userId) {
        // 1. 用户地址（用户的数据库中）
        List<UserAddressDTO> userAddressListByUserId = userApiClient.findUserAddressListByUserId(userId);
        // 2. 订单详情
        List<CartInfoDTO> cartCheckedList = cartApiClient.getCartCheckedList(userId);
        List<OrderDetailDTO> orderDetailDTOS = cartInfoConverter.convertCartInfoDTOToOrderDetailDTOList(cartCheckedList);
        // 3. 总数量
        Optional<Integer> totalNum = orderDetailDTOS.stream().map(OrderDetailDTO::getSkuNum).reduce(Integer::sum);
        // 4. 总价格
        Optional<BigDecimal> totalPrice = orderDetailDTOS.stream().map(OrderDetailDTO::getOrderPrice).reduce(BigDecimal::add);
        // 放入值
        OrderTradeDTO orderTradeDTO = new OrderTradeDTO();
        orderTradeDTO.setUserAddressList(userAddressListByUserId);
        orderTradeDTO.setDetailArrayList(orderDetailDTOS);
        orderTradeDTO.setTotalNum(totalNum.get());
        orderTradeDTO.setTotalAmount(totalPrice.get());
        return orderTradeDTO;
    }
}
