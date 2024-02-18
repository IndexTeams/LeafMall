package cn.kiroe.mall.mq.producer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

/**
 * @Author Kiro
 * @Date 2024/01/31 21:18
 **/
class BaseProducerTest {

    public static void main(String[] args) throws Exception {
        // 初始化一个producer并设置Producer group name
        DefaultMQProducer producer = new DefaultMQProducer("producerTest"); //（1）
        // 设置NameServer地址
        producer.setNamesrvAddr("jfl.kiroe.cn:9876");  //（2）
        // 启动producer
        producer.start();
        // 创建一条消息，并指定topic、tag、body等信息，tag可以理解成标签，对消息进行再归类，RocketMQ可以在消费端对tag进行过滤
        Message msg = new Message("TopicTest1" /* Topic */,
                "TagA" /* Tag */,
                ("Hello RocketMQ ").getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
        );   //（3）
        // 利用producer进行发送，并同步等待发送结果
        SendResult sendResult = producer.send(msg);   //（4）
        System.out.printf("%s%n", sendResult);
        producer.shutdown();
    }


}



