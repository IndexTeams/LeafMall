package cn.kiroe.mall.product.mq.consumer;

import cn.kiroe.mall.canal.constant.CanalConstant;
import cn.kiroe.mall.canal.model.CanalMessage;
import cn.kiroe.mall.common.constant.RedisConst;
import cn.kiroe.mall.product.model.SkuInfo;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author Kiro
 * @Date 2024/02/01 23:30
 **/
@Slf4j
@Component
public class SkuCanalConsumer {
    @Value("${rocketmq.namesrv.addr}")
    String namesrv;
    @Value("${rocketmq.consumer.group}")
    String consumerGroup;
    @Autowired
    RedissonClient redissonClient;

    // 用于发送请求
    @PostConstruct
    public DefaultMQPushConsumer skuCanalConsumer() {
        try {
            log.info("初始化SkuCanalConsumer消费者");
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();
            consumer.setNamesrvAddr(namesrv);
            consumer.setConsumerGroup(consumerGroup + "SkuCanalConsumer");
            consumer.subscribe(CanalConstant.TOPIC, "*");
            consumer.setMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(final List<MessageExt> msgs, final ConsumeConcurrentlyContext context) {
                    try {
                        for (final MessageExt msg : msgs) {
                            String bodyJson = new String(msg.getBody());
                            CanalMessage canalMessage = JSON.parseObject(bodyJson, CanalMessage.class);
                            log.info("数据库修改的数据为:{}", canalMessage.toString());
                            // 判断是否为 skuInfo,如果不是select则 清除缓存
                            TableInfo tableInfo = TableInfoHelper.getTableInfo(SkuInfo.class);
                            if (tableInfo.getTableName().equals(canalMessage.getTable())
                                && ("UPDATE".equals(canalMessage.getType())
                                || "DELETE".equals(canalMessage.getType()))) {
                                log.info("准备删除redis中的skuInfo缓存");
                                List<Map<String, Object>> data = canalMessage.getData();
                                for (final Map<String, Object> item : data) {
                                    RBucket<Object> bucket = redissonClient.getBucket(RedisConst.SKUKEY_PREFIX + item.get(canalMessage.getPkNames().get(0)));
                                    // 删除对象
                                    bucket.delete();
                                }
                                log.info("删除skuInfo缓存成功");
                            }
                        }
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


}
