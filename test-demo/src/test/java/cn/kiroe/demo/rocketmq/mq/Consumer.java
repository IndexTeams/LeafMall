package cn.kiroe.demo.rocketmq.mq;


import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * 接收并消费消息
 *
 * @Author Kiro
 * @Date 2024/01/31 15:49
 **/
public class Consumer {

    public static void main(String[] args) throws MQClientException {
        // 1. 创建一个消息消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer-group");
        // 2. 设置注册中心
        consumer.setNamesrvAddr("jfl.kiroe.cn:9876");
        // 3. 订阅主题
        consumer.subscribe("demo-topic2", "*");
        // 4. 设置一个消息监听器
        // 消费消息的方法
        // 监听器会监听消息，每次收到后，会调用 consumeMessage方法来消费消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(final List<MessageExt> msgs, final ConsumeConcurrentlyContext context) {
                try {
                    MessageExt messageExt = msgs.get(0);
                    System.out.println("messageExt.getBody() = " + new String(messageExt.getBody()));
                    System.out.println("System.currentTimeMillis() = " + System.currentTimeMillis());
                    System.out.println("messageExt.getMsgId() = " + messageExt.getMsgId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 5. 启动
        consumer.start();

    }

    /**
     * 不能直接使用 test会无法阻塞
     *
     * @throws MQClientException
     */
    @Test
    void test() throws MQClientException {
        // 1. 创建一个消息消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("consumer-group-55");
        // 2. 设置注册中心
        consumer.setNamesrvAddr("jfl.kiroe.cn:9876");
        // 3. 订阅主题
        consumer.subscribe("demo-topic", "*");
        // 4. 设置一个消息监听器
        // 消费消息的方法
        // 监听器会监听消息，每次收到后，会调用 consumeMessage方法来消费消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(final List<MessageExt> msgs, final ConsumeConcurrentlyContext context) {
                System.out.println("test");
                return null;
            }
        });
        // 5. 启动
        consumer.start();
    }
}
