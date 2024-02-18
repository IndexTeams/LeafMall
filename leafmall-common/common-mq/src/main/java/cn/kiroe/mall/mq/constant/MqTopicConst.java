package cn.kiroe.mall.mq.constant;

import cn.kiroe.mall.mq.util.DelayTimeUtil;

public class MqTopicConst {


    // 商品上架
    public static String PRODUCT_ONSALE_TOPIC = "product_onsale_topic";

    // 商品下架
    public static String PRODUCT_OFFSALE_TOPIC = "product_offsale_topic";

    // 延迟取消订单Topic
    public static String DELAY_ORDER_TOPIC = "delay_order_topic";

    // 订单超时取消 延迟级别，延迟时间
    // 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
    // 1  2  3   4   5  6  7  8  9  10 11 12 13 14  15  16  17 18
    public static Integer DELAY_ORDER_LEVEL = 18;
    public static Long DELAY_ORDER_LEVEL_TIME = (long) DelayTimeUtil.getDelayTimeByLevel(DELAY_ORDER_LEVEL);

    // 秒杀抢购队列
    public static String SECKILL_GOODS_QUEUE_TOPIC = "seckill_goods_queue_topic";


}
