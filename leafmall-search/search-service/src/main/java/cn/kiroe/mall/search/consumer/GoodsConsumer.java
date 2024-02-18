package cn.kiroe.mall.search.consumer;

import cn.kiroe.mall.mq.constant.MqTopicConst;
import cn.kiroe.mall.search.service.SearchService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MQConsumer;
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
 * @Date 2024/01/31 20:09
 **/
@Configuration
@Slf4j
public class GoodsConsumer {
    @Value("${rocketmq.namesrv.addr}")
    String namesrvAddr;
    @Value("${rocketmq.consumer.group}")
    String consumerGroup;
    @Autowired
    SearchService searchService;

    /**
     * 调用方法
     *
     * @return
     */
    @Bean
    MQConsumer upperGoodsConsumer() {
        try {
            log.info("开始初始化上架Consumer");
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
            consumer.setConsumerGroup(consumerGroup+"GoodsConsumer_upperGoods");
            consumer.setNamesrvAddr(namesrvAddr);
            consumer.subscribe(MqTopicConst.PRODUCT_ONSALE_TOPIC, "*");
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(final List<MessageExt> msgs, final ConsumeConcurrentlyContext context) {
                    try {
                        log.info("开始消费GoodsConsumer_upperGoods");
                        MessageExt messageExt = msgs.get(0);
                        String msg = new String(messageExt.getBody());
                        Long skuId = JSON.parseObject(msg, Long.class);
                        searchService.upperGoods(skuId);
                        log.info("执行上架成功skuId:{}", skuId);
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    } catch (Exception e) {
                        log.error("消费lowerGoodsConsumer错误", e);
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                }
            });
            log.info("upperGoodsConsumer消费者启动");
            consumer.start();
            return consumer;
        } catch (MQClientException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    MQConsumer lowerGoodsConsumer() {
        try {
            log.info("开始初始化下架Consumer");
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
            consumer.setConsumerGroup(consumerGroup+"GoodsConsumer_lowerGoods");
            consumer.setNamesrvAddr(namesrvAddr);
            consumer.subscribe(MqTopicConst.PRODUCT_OFFSALE_TOPIC, "*");
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(final List<MessageExt> msgs, final ConsumeConcurrentlyContext context) {
                    try {
                        log.info("开始消费lowerGoodsConsumer");
                        MessageExt messageExt = msgs.get(0);
                        String msg = new String(messageExt.getBody());
                        Long skuId = JSON.parseObject(msg, Long.class);
                        searchService.lowerGoods(skuId);
                        log.info("执行下架成功skuId:{}", skuId);
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    } catch (Exception e) {
                        log.error("消费lowerGoodsConsumer错误", e);
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                }
            });
            log.info("upperGoodsConsumer消费者启动");
            consumer.start();
            return consumer;
        } catch (Exception e) {
            log.error("错误!!", e);
            throw new RuntimeException(e);
        }

    }
}


