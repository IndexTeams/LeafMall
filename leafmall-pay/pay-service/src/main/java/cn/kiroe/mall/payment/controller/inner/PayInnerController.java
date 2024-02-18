package cn.kiroe.mall.payment.controller.inner;

import cn.kiroe.mall.common.result.Result;
import cn.kiroe.mall.pay.api.dto.AlipayTradeQueryResponseDTO;
import cn.kiroe.mall.pay.api.dto.PaymentInfoDTO;
import cn.kiroe.mall.payment.service.AliPayService;
import cn.kiroe.mall.payment.service.PayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Kiro
 * @Date 2024/02/03 15:26
 **/
@RestController
@RequiredArgsConstructor
public class PayInnerController {
    private final PayService payService;
    private final AliPayService aliPayService;

    /**
     * 根据外部订单号 查询支付记录
     */
    @GetMapping("/api/payment/inner/getPaymentInfoByOutTradeNo/{outTradeNo}")
    PaymentInfoDTO getPaymentInfoDTOByOutTradeNo(@PathVariable(value = "outTradeNo") String outTradeNo){
        return payService.getPaymentInfoDTOByOutTradeNo(outTradeNo);

    }

    /**
     * 根据外部订单号 查询支付宝支付状态
     */
    @GetMapping("/api/payment/inner/getAlipayInfo/{outTradeNo}")
    AlipayTradeQueryResponseDTO getAlipayInfo(@PathVariable(value = "outTradeNo") String outTradeNo){
        return aliPayService.getAlipayInfo(outTradeNo);
    }

    /**
     * 关闭支付宝支付记录
     */
    @GetMapping("/api/payment/inner/closeAlipay/{outTradeNo}")
    Result closeAlipay(@PathVariable(value = "outTradeNo") String outTradeNo){
        boolean isClosed =  aliPayService.closeAliPay(outTradeNo);
        return isClosed?Result.ok():Result.fail();
    }

    /**
     * 修改paymentInfo为已关闭
     */
    @GetMapping("/api/payment/inner/closePaymentInfo/{outTradeNo}")
    Result closePaymentInfo(@PathVariable(value = "outTradeNo") String outTradeNo){
        boolean isClosed =  payService.closePaymentInfo(outTradeNo);
        return isClosed?Result.ok():Result.fail();
    }

}
