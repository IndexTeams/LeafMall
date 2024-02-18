package cn.kiroe.mall.product.mq.producer;

import cn.kiroe.mall.mq.constant.MqTopicConst;
import cn.kiroe.mall.mq.producer.BaseProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author Kiro
 * @Date 2024/01/31 19:49
 **/
@Component
@RequiredArgsConstructor
@Slf4j
public class SkuProducer {
    private final BaseProducer baseProducer;

    public boolean upperGoods(Long skuId){
        log.info("发送上架消息");
        return baseProducer.sendMessage(MqTopicConst.PRODUCT_ONSALE_TOPIC, skuId);
    }

    public boolean lowerGoods(Long skuId){
        log.info("发送下架消息");
        return baseProducer.sendMessage(MqTopicConst.PRODUCT_OFFSALE_TOPIC,skuId);
    }

}
