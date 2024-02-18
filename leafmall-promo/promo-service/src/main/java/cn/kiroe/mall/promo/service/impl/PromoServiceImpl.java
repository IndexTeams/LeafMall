package cn.kiroe.mall.promo.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.kiroe.mall.common.constant.RedisConst;
import cn.kiroe.mall.common.result.Result;
import cn.kiroe.mall.promo.api.dto.SeckillGoodsDTO;
import cn.kiroe.mall.promo.constant.SeckillGoodsStatus;
import cn.kiroe.mall.promo.constant.StockStatus;
import cn.kiroe.mall.promo.converter.SeckillGoodsConverter;
import cn.kiroe.mall.promo.mapper.SeckillGoodsMapper;
import cn.kiroe.mall.promo.model.OrderRecord;
import cn.kiroe.mall.promo.model.SeckillGoods;
import cn.kiroe.mall.promo.service.PromoService;
import cn.kiroe.mall.promo.util.LocalCacheHelper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.*;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Kiro
 * @Date 2024/02/05 15:00
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class PromoServiceImpl implements PromoService {
    private final SeckillGoodsMapper seckillGoodsMapper;
    private final RedissonClient redissonClient;
    private final SeckillGoodsConverter seckillGoodsConverter;

    /**
     * 缓存预热，导入秒杀商品
     */
    @Override
    public void importIntoRedis() {
        // 1. 查询出今天需要参与秒杀活动的商品
        List<SeckillGoods> seckillGoods = seckillGoodsMapper.selectList(new QueryWrapper<SeckillGoods>()
                .eq("DATE_FORMAT(start_time,'%Y-%m-%d')", DateUtil.format(new Date(), "yyyy-MM-dd"))
                .lambda()
                .eq(SeckillGoods::getStatus, SeckillGoodsStatus.CHECKED_PASS)
                .gt(SeckillGoods::getStockCount, 0));
        if (seckillGoods == null) {
            log.info("今天没有需要参与秒杀的商品");
            return;
        }
        // 2. 把秒杀活动的商品存入redis
        // 使用hash储存
        // key: 前缀+今天日期
        // field: 商品id
        // value: 秒杀商品对象
        RMap<Long, SeckillGoods> seckillGoodsRMap = redissonClient.getMap(RedisConst.PROMO_SECKILL_GOODS);
        seckillGoodsRMap.clear();// 清空之前的内容
        seckillGoodsRMap.putAll(seckillGoods.stream().collect(Collectors.toMap(SeckillGoods::getSkuId, s -> s)));
        // 3. 初始化商品的库存队列
        // key为: 前缀+skuid
        // value为 list个数
        for (final SeckillGoods seckillGood : seckillGoods) {
            RDeque<Object> deque = redissonClient.getDeque(RedisConst.PROMO_SECKILL_GOODS_STOCK_PREFIX + seckillGood.getSkuId());
            // 为了避免重复添加,清除
            deque.clear();
            // 添加库存长度至list中
            for (int i = 0; i < seckillGood.getStockCount(); i++) {
                deque.add(seckillGood.getSkuId());
            }
        }
        // 4. 初始化各个商品的 内容状态位,当为1表示有库存，0表示没有库存
        // 初始化时一定有库存，当没有库存时设置 为没有库存
        for (final SeckillGoods seckillGood : seckillGoods) {
            LocalCacheHelper.put(String.valueOf(seckillGood.getSkuId()), StockStatus.HAS_STOCK);
        }


    }

    /**
     * 从redis中获取列表
     *
     * @return
     */
    @Override
    public List<SeckillGoodsDTO> findAll() {
        RMap<Long, SeckillGoods> seckillGoodsRMap = redissonClient.getMap(RedisConst.PROMO_SECKILL_GOODS);
        List<SeckillGoods> seckillGoodsList = seckillGoodsRMap.values().stream().toList();
        return seckillGoodsConverter.convertSeckillGoodsList(seckillGoodsList);
    }

    @Override
    public SeckillGoodsDTO getSeckillGoodsDTO(final Long skuId) {
        // 获取秒杀商品详情页
        RMap<Long, SeckillGoods> map = redissonClient.getMap(RedisConst.PROMO_SECKILL_GOODS);
        SeckillGoods seckillGoods = map.get(skuId);
        return seckillGoodsConverter.convertSeckillGoodsToDTO(seckillGoods);
    }

    @Override
    public Result checkOrder(final Long skuId, final String userId) {
        return null;
    }

    /**
     * 描述下单，消息开始消费
     *
     * @param userId
     * @param skuId
     */
    @Override
    public void seckillOrder(final String userId, final Long skuId) {
        log.info("开始下单消费");
        // 1. 判断本地库存状态位
        Object localStockStatus = LocalCacheHelper.get(skuId.toString());
        if (StockStatus.NO_STOCK.equals(localStockStatus)) {
            log.info("本地状态位显示没有缓存");
            return;
        }
        // 2. 标记用户下单
        // 2.1 利用setnx命令，防止秒杀下单的消息重复消费
        String key = RedisConst.PROMO_USER_ORDERED_FLAG + userId;
        RBucket<Long> bucket = redissonClient.getBucket(key);
        bucket.set(Long.valueOf(userId));
        // 2.2 将redis中添加一个标记，方便后续前端查询消费情况
        RDeque<Object> deque = redissonClient.getDeque(RedisConst.PROMO_SECKILL_GOODS_STOCK_PREFIX + skuId);
        if (CollUtil.isEmpty(deque)) {
            // 设置没有库存
            LocalCacheHelper.put(skuId.toString(), StockStatus.NO_STOCK);
            if (!bucket.trySet(skuId)) {// 设置失败，已经有值了,返回
                log.info("已经购买过了");
                return;
            }
            log.info("没有库存了");
            return;
        }
        // 3. 扣减库存
        Object object = deque.poll();
        if (object == null) {
            LocalCacheHelper.put(skuId.toString(), StockStatus.NO_STOCK);
            log.info("没有库存了");
            return;
        }
        // 4. 标记用户下单
        // 说明用户扣钱库存成功了，库存也被弹出，用户被抢到了
        // 标记的作用:
        // 4.1 保存用户的临时订单信息
        // 4.2 后续检测排队情况，可以更具这个标记来检查
        // 使用hash在存储这个标记
        // key:promo:orders, field:userid:, vale: 临时订单记录,
        log.info("开始减库存");
        RMap<String, OrderRecord> map = redissonClient.getMap(RedisConst.PROMO_SECKILL_ORDERS);
        SeckillGoods seckillGoods = seckillGoodsConverter.convertSeckillDTO(getSeckillGoodsDTO(skuId));
        OrderRecord orderRecord = OrderRecord.builder()
                                             .num(1).userId(userId)
                                             .seckillGoods(seckillGoods).build();
        map.put(userId, orderRecord);
        // 5. 更新同步库存,如果需要redis中的商品库存，和数据库中的商品保持一致，那么在这里加锁
        // 这里加锁
        String lockKey = RedisConst.PROMO_LOCK_SKUID + skuId;
        RLock lock = redissonClient.getLock(lockKey);
        try {
            lock.lock();
            seckillGoods.setStockCount(deque.size());
            // 更新redis中的商品
            RMap<Long, SeckillGoods> promoGoodsMap = redissonClient.getMap(RedisConst.PROMO_SECKILL_GOODS);
            promoGoodsMap.put(skuId, seckillGoods);
            // 更新数据库
            log.info("更新数据库中");
            seckillGoodsMapper.update(new LambdaUpdateWrapper<SeckillGoods>()
                    .set(SeckillGoods::getStockCount, deque.size())
                    .eq(SeckillGoods::getSkuId, skuId));
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void clearRedisCache() {
        // 删除本地
        RKeys keys = redissonClient.getKeys();
        keys.deleteByPattern(RedisConst.PROMO_PREFIX + "*");
        redissonClient.getMap(RedisConst.LOCAL_CACHE_HELPER).clear();
    }
}
