package cn.kiroe.mall.promo.mq.consumer;

import cn.kiroe.mall.mq.constant.MqTopicConst;
import cn.kiroe.mall.promo.model.UserRecord;
import cn.kiroe.mall.promo.service.PromoService;
import com.alibaba.fastjson.JSON;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 消费秒杀下单的消息
 *
 * @Author Kiro
 * @Date 2024/02/06 10:54
 **/
@Component
@RequiredArgsConstructor
@Slf4j
public class PromoConsumer {
    @Value("${rocketmq.consumer.group}")
    String groupName;
    @Value("${rocketmq.namesrv.addr}")
    String addr;
    // 声明一个消息消费者
    DefaultMQPushConsumer consumer;
    private final PromoService promoService;

    /**
     *
     * @throws MQClientException
     */
    @PostConstruct
    public void init() throws MQClientException {
        consumer = new DefaultMQPushConsumer(groupName);
        consumer.setNamesrvAddr(addr);
        consumer.subscribe(MqTopicConst.SECKILL_GOODS_QUEUE_TOPIC, "*");
        log.info("初始化消费者");
        // 设置监听器
        consumer.setMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(final List<MessageExt> msgs, final ConsumeConcurrentlyContext context) {
                try {
                    // 1.获取消息内容
                    log.info("秒杀开始消费，消息内容:{}",new String(msgs.get(0).getBody()));
                    String jsonStr = new String(msgs.get(0).getBody());
                    UserRecord userRecord = JSON.parseObject(jsonStr, UserRecord.class);
                    // 2. 调用接口，排队消息消费
                    promoService.seckillOrder(userRecord.getUserId(),userRecord.getSkuId());
                    log.info("秒杀消费结束");
                    // 3. 返回正确
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }catch (Exception e){
                    log.error("消费错误",e);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }

            }
        });
        log.info("开启消费者");
        consumer.start();

    }
}
