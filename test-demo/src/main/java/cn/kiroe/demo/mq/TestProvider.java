package cn.kiroe.demo.mq;

import cn.kiroe.mall.mq.producer.BaseProducer;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @Author Kiro
 * @Date 2024/02/02 09:06
 **/
@Component
@RequiredArgsConstructor
public class TestProvider {

    @PostConstruct
    public void init(){
        RocketMQTemplate rocketMQTemplate = new RocketMQTemplate();
        

    }
}
