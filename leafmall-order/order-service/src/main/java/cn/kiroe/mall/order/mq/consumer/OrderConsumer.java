package cn.kiroe.mall.order.mq.consumer;

import cn.kiroe.mall.mq.constant.MqTopicConst;
import cn.kiroe.mall.order.client.PayApiClient;
import cn.kiroe.mall.order.constant.OrderStatus;
import cn.kiroe.mall.order.dto.OrderInfoDTO;
import cn.kiroe.mall.order.service.OrderService;
import cn.kiroe.mall.pay.api.constant.AliPaymentTradeStatus;
import cn.kiroe.mall.pay.api.constant.PaymentStatus;
import cn.kiroe.mall.pay.api.dto.AlipayTradeQueryResponseDTO;
import cn.kiroe.mall.pay.api.dto.PaymentInfoDTO;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Author Kiro
 * @Date 2024/02/01 15:57
 **/
@Configuration
@Slf4j
public class OrderConsumer {
    @Value("${rocketmq.namesrv.addr}")
    String namesrv;
    @Value("${rocketmq.consumer.group}")
    String consumerGroup;
    @Autowired
    OrderService orderService;
    @Autowired
    PayApiClient payApiClient;

    /**
     * 防止支付宝刚好支付时，启动了该消费者
     * 所以需要判断 用户是否支付了
     * 如果用户支付了，则不要设置过期
     * 为了减少对应 支付宝的请求，所以先判断
     * 订单表,支付表, 然后再是 支付宝的支付表
     * 直接这样嵌套吗？
     *
     * @return
     */
    @Bean
    public DefaultMQPushConsumer init() {
        try {
            log.info("初始化定时订单取消消费者");
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
            consumer.setNamesrvAddr(namesrv);
            consumer.setConsumerGroup(consumerGroup + "OrderConsumer");
            consumer.subscribe(MqTopicConst.DELAY_ORDER_TOPIC, "*");
            consumer.setMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(final List<MessageExt> msgs, final ConsumeConcurrentlyContext context) {
                    try {
                        log.info("开始消费");
                        MessageExt messageExt = msgs.get(0);
                        String json = new String(messageExt.getBody());
                        Long orderId = JSON.parseObject(json, Long.class);
                        // 1.获取当前订单信息
                        OrderInfoDTO orderInfo = orderService.getOrderInfo(orderId);
                        // 判断是否应该 被过期
                        boolean shouldExpire = checkOrderStatusShouldExpire(orderInfo);
                        if (shouldExpire) {
                            // 设置过期
                            orderService.execExpiredOrder(orderId);
                        }
                        log.info("结束消费");
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    } catch (Exception e) {
                        log.info("消费错误", e);
                        throw new RuntimeException(e);
                    }
                }


            });
            // 开启消费
            consumer.start();
            return consumer;
        } catch (MQClientException e) {
            log.error("错误", e);
            throw new RuntimeException(e);
        }
    }

    private boolean checkOrderStatusShouldExpire(final OrderInfoDTO orderInfo) {
        // 判断订单状态
        if (OrderStatus.UNPAID.name().equals(orderInfo.getOrderStatus())) {
            return true;
        }
        // 2.查看订单记录中，是否有对应状态
        PaymentInfoDTO paymentInfoDTO = payApiClient.getPaymentInfoDTOByOutTradeNo(orderInfo.getOutTradeNo());
        if (paymentInfoDTO == null) {
            // 3.1 如果没有该记录，过期订单
            return true;
        }
        // 如果为已经支付，不做操作
        if (PaymentStatus.PAID.name().equals(paymentInfoDTO.getPaymentStatus())) {
            return false;
        }
        // 3. 如果有记录，查询支付宝中是否有这笔订单的交易记录
        AlipayTradeQueryResponseDTO alipayInfo = payApiClient.getAlipayInfo(orderInfo.getOutTradeNo());
        // 3.2 如果有该记录,判断状态 //
        if (alipayInfo == null) {
            return true;
        }
        // 3.2.1 TRADE_SUCCESS 交易成功,返回Consume_SUCCESS;
        if (AliPaymentTradeStatus.TRADE_SUCCESS.equals(alipayInfo.getTradeStatus())) {
            return false;
        }
        // 3.2.2 交易失败,关闭支付宝，关闭订单
        // WAIT_BUYER_PAY, 关闭支付宝
        // TRADE_CLOSED, 过期
        payApiClient.closePaymentInfo(orderInfo.getOutTradeNo());
        if (AliPaymentTradeStatus.WAIT_BUYER_PAY.equals(alipayInfo.getTradeStatus())) {
            // 通过订单id，将设置为 取消的状态
            payApiClient.closeAlipay(orderInfo.getOutTradeNo());
        }
        return true;
    }
}
