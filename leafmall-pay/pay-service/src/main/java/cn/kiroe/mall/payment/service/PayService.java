package cn.kiroe.mall.payment.service;

import cn.kiroe.mall.order.dto.OrderInfoDTO;
import cn.kiroe.mall.pay.api.dto.PaymentInfoDTO;
import cn.kiroe.mall.payment.vo.PaymentNotification;

public interface PayService {

    /**
     * 支付宝支付，获取支付表单
     */
    String createAliPay(Long orderId);

    /**
     * 保存支付记录
     */
    void savePaymentInfo(OrderInfoDTO orderInfo, String paymentTypeName);

    /**
     * 通过外部交易流水号和交易渠道 查询支付记录
     */
    PaymentInfoDTO queryPaymentInfoByOutTradeNoAndPaymentType(String outTradeNo, String payTypeName);

    String callBackNotify(PaymentNotification paymentNotification);

    PaymentInfoDTO getPaymentInfoDTOByOutTradeNo(String outTradeNo);

    boolean closePaymentInfo(final String outTradeNo);
}
