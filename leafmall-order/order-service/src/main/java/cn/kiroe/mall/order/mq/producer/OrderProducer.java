package cn.kiroe.mall.order.mq.producer;

import cn.kiroe.mall.mq.constant.MqTopicConst;
import cn.kiroe.mall.mq.producer.BaseProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * order的生产者
 *
 * @Author Kiro
 * @Date 2024/02/01 15:49
 **/
@Component
@RequiredArgsConstructor
@Slf4j
public class OrderProducer {
    private final BaseProducer baseProducer;

    /**
     * 设置一个 订单超时的 消息
     *
     * @param orderId
     */
    public void setOrderTimeOut(final Long orderId) {
        // 设置延迟消息
        baseProducer.sendDelayMessage(MqTopicConst.DELAY_ORDER_TOPIC, orderId, MqTopicConst.DELAY_ORDER_LEVEL);
    }

}
