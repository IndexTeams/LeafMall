package cn.kiroe.mall.payment.service;

import cn.kiroe.mall.pay.api.dto.AlipayTradeQueryResponseDTO;

/**
 * @Author Kiro
 * @Date 2024/02/03 15:48
 **/
public interface AliPayService {
     AlipayTradeQueryResponseDTO getAlipayInfo(final String outTradeNo);

     boolean closeAliPay(String outTradeNo);
}
