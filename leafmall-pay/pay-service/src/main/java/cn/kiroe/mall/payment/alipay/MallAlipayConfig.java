package cn.kiroe.mall.payment.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 创建日期: 2023/03/17 14:39
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "alipay.config")
public class MallAlipayConfig {

    String appId;

    String alipayUrl;

    String appPrivateKey;

    String alipayPublicKey;

    // 同步回调地址
    String returnPaymentUrl;

    // 异步回调地址
     String notifyPaymentUrl;
     // 返回的前端页面地址
     String returnOrderUrl;

    String format;
    String charset;
    String signType;


    // 支付宝客户端
    @Bean
    public AlipayClient alipayClient() throws AlipayApiException {

        AlipayConfig alipayConfig = new AlipayConfig();

        alipayConfig.setServerUrl(alipayUrl);
        alipayConfig.setAppId(appId);
        alipayConfig.setPrivateKey(appPrivateKey);
        alipayConfig.setFormat(format);
        alipayConfig.setAlipayPublicKey(alipayPublicKey);
        alipayConfig.setCharset(charset);
        alipayConfig.setSignType(signType);
        return new DefaultAlipayClient(alipayConfig);
    }
}

