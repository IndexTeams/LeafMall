package cn.kiroe.mall.payment.service.impl;

import cn.kiroe.mall.pay.api.dto.AliQueryResponse;
import cn.kiroe.mall.pay.api.dto.AlipayTradeQueryResponseDTO;
import cn.kiroe.mall.payment.service.AliPayService;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeCloseModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author Kiro
 * @Date 2024/02/03 15:49
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class AliPayServiceImpl implements AliPayService {

    private final AlipayClient alipayClient;

    @SneakyThrows
    @Override
    public AlipayTradeQueryResponseDTO getAlipayInfo(final String outTradeNo) {
        log.info("开始查询支付宝支付信息outTradeNo:{}", outTradeNo);
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        model.setOutTradeNo(outTradeNo);
        request.setBizModel(model);
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        if (response.isSuccess()) {
            String jsonBody = response.getBody();
            log.info("支付宝查询的结果为：{}", jsonBody);
            AliQueryResponse aliQueryResponse = JSON.parseObject(jsonBody, AliQueryResponse.class);
            return aliQueryResponse.getAlipayTradeQueryResponse();
        }
        // 失败返回null
        log.info("支付宝支付信息没有查询到outTradeNo:{}", outTradeNo);
        return null;
    }

    @SneakyThrows
    @Override
    public boolean closeAliPay(final String outTradeNo) {
        log.info("开始关闭支付宝的支付");
        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
        AlipayTradeCloseModel bizModel = new AlipayTradeCloseModel();
        bizModel.setOutTradeNo(outTradeNo);
        request.setBizModel(bizModel);
        AlipayTradeCloseResponse response = alipayClient.execute(request);
        String body = response.getBody();
        log.info("关闭支付宝支付成功{}",body);
        return true;
    }
}
