package cn.kiroe.mall.payment.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.kiroe.mall.common.result.Result;
import cn.kiroe.mall.order.dto.OrderInfoDTO;
import cn.kiroe.mall.payment.alipay.MallAlipayConfig;
import cn.kiroe.mall.payment.client.OrderApiClient;
import cn.kiroe.mall.payment.service.PayService;
import cn.kiroe.mall.payment.vo.PaymentNotification;
import com.alibaba.fastjson.JSON;
import com.alipay.api.internal.util.AlipaySignature;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author Kiro
 * @Date 2024/02/02 09:46
 **/
@Controller
@Slf4j
@RequiredArgsConstructor
public class PayController {
    private final OrderApiClient orderApiClient;
    private final PayService payService;
    private final MallAlipayConfig alipayConfig;

    @ResponseBody
    @GetMapping("pay/auth")
    public Result<OrderInfoDTO> payIndex(Long orderId) {
        // 调用订单服务获取订单信息
        OrderInfoDTO orderInfoDTO = orderApiClient.getOrderInfoDTO(orderId);
        return Result.ok(orderInfoDTO);
    }

    @RequestMapping("/pay/alipay/submit/{orderId}")
    @ResponseBody
    public String submitOrder(@PathVariable Long orderId) {
        return payService.createAliPay(orderId);
    }

    /**
     * 支付成功：同步回调
     */
    @RequestMapping("/pay/alipay/callback/return")
    public String callBack() {
        log.info("支付成功，同步回调！ ");
        // 同步回调给用户展示信息
        return "redirect:" + alipayConfig.getReturnOrderUrl();
    }

    /**
     * 支付成功：异步回调
     */
    @PostMapping("/pay/alipay/callback/notify")
    @ResponseBody
    @SneakyThrows
    public String callbackNotify(@RequestParam Map<String, String> paramsMap) {
        log.info("支付成功，异步回调，paramMap:{}", JSON.toJSONString(paramsMap));
        // 1. 验证签名
        boolean isChecked = AlipaySignature.rsaCheckV1(paramsMap, alipayConfig.getAlipayPublicKey(), alipayConfig.getCharset(), alipayConfig.getSignType());
        if (!isChecked) {
            log.info("验证签名失败");
            return "fail";
        }
        PaymentNotification paymentNotification = BeanUtil.fillBeanWithMap(paramsMap, new PaymentNotification(), true);
        return payService.callBackNotify(paymentNotification);
    }
}
