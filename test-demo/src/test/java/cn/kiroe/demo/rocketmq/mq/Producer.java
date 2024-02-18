package cn.kiroe.demo.rocketmq.mq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.junit.jupiter.api.Test;

import java.util.Date;

/**
 * @Author Kiro
 * @Date 2024/01/31 15:50
 **/
public class Producer {
    @Test
    void test() throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        // 1. 创建一个消费的生产者,指定组名
        DefaultMQProducer producer = new DefaultMQProducer("demo-producer-55");
        // 2. 设置注册中心
        producer.setNamesrvAddr("jfl.kiroe.cn:9876");
        // 3. 启动消息生产者
        producer.start();
        // 4. 创建一个消息对象   // 如果设置延迟发送，需要在配置文件中设置等级，
        Message msg = new Message();
        msg.setBody(String.valueOf(System.currentTimeMillis()).getBytes());
        msg.setTopic("demo-topic2");
        // 设置延迟等级
        //msg.setDelayTimeLevel(3);
        // 5. 发送消息
        SendResult sendResult = producer.send(msg);
        MessageQueue messageQueue = sendResult.getMessageQueue();
        System.out.println("sendResult.getMsgId() = " + sendResult.getMsgId());
        System.out.println("messageQueue.getTopic() = " + messageQueue.getTopic());

    }

    public static void main(String[] args) {

    }
}
