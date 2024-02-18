package cn.kiroe.demo.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.diagnosis.DiagnosisUtils;
import com.alipay.api.domain.AlipayTradeCloseModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Kiro
 * @Date 2024/02/02 10:58
 **/
@RestController
@Slf4j
public class PayController {
    static String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDG6UqzdSViOpf3djaat7hLWJzSdZFf3VzQg6OOot4yHKsXrfXmkZVCFWemLG0fDpJ6r8loMOmvQoYwAM58Rut1GAqI4+EC3AUa2JBGNJAKSq85gKkUVDnuNH1PMAmLe7E0Vg4CXntojIPwfEyiW2LD406WEQEmXeHGodkKDbswB/zEWIYJ4QjCKow0OZ5lxpRpOVwFVg9Z0+wWJxXv6LfUNL8BuKyVgAaQrmn1CrrBkJFGsjmjdnVgub9d0MFADTrYYGyEySfrJkFCDLKzSNzNiYbXHwnOesWNW5yFHr2dHSiZoEcTLSKFwlnY33zsYzK8brrd0tdfINm2DKAsRnrrAgMBAAECggEAX3su+5P5HPzpY8VgEsar9acUqEX9QZo96m22PkraBA4U5un/hu1NS4qA1ZivzN+u2qu1L4mDMzjotvQ8KhXVAsFGlU1l/nxlCuz33Gtw1S/iiuTCxNplcH3LxQ5XFpiKiIfic6qs7JO+5dqU4r6hW0yqtYh2F9erADFCwPu0ZiomKbDmS8JAOzF7okQlTlw0xzUI8eFZp0ocD8NI5OUWexT6Fv7Gao0JbxycnuZ4aRCNRVs//IkhLqn1TH61lNBXA/honDAedQB6idxEj6hiF3rG0NGGqLKK5oUDqVINWlmV4To3kkacwlL7H3VWXYn0Ji402fiuGnCKQJ7DG6HhGQKBgQDyprvCKwR/FTofr+9LognXqEXg/2EB+pKm6DbErTUaxHyllN9W/HTnPgV+/nza3QafP+5wLP4eSL3G45TmLHjx82oiqU7Z7e0pYIVQHAP6J4xuSBMO4b7/uAjHltEZTkvKmrnk6ri9AhUUp1wxJ+shyHA1BMcZyJrg6wPT7Jyq1wKBgQDR2pD9eq73quQMTNOPEWwqgo2+TBvfTNEwRFSdp5dn1xxFf5+aYdFzlAETCVfWqyL+hLKbEnuUzXejPaZFLZZrDavAcP3cDdDpYU6AAkMrnGuBEJgfIpguq09Y75djH2R+H9cNyrEo11M5cG3KL4pnGKzvbF083/4FFnH2QoDiDQKBgQDZ/SgYW4UClhNNfETB8aRd9XIxm7uOh4lQILVzIxfeID7Kkl/CSbLMO0+8sut/w6i7UDfo1sLEehzjnHib/7/3vPHQicGozrKMxYV+XmzEQttKB733VJ6+JJrek4Lt9QTp0u7F1Ih6ZHCDyYZtMqtuAh+rwcsMQsfA3+DkdmCYqQKBgQCs2cfFUbEehN8UFo/HbwVWOnTAAvaORjqj9BRySyGVwYQi5o0y31FjTsxXvsFgK1RGqkxnGKE8HsVlaxkyL+uOwBLxDuC6yZKDOMgs/jAPkualx2JVeuw4vJg93BMo7SmcDWPYk1S7HCkLzcRQ3jm004lWKD7gLmuQCz3RJlP8XQKBgB93AskVk8Ibt8TqBYsK0exzhaZCRWGIXnqv/8Sa7DOjcXnMCN4eTikkI+gMUwTnZzXMhxchy+05VAXuPU3I5eSyxiGYZg+YAWSyFUjfYIV9rxuFl3ckFLuhBuGTaHIxff4BhEEtOU95/CP7MD3IU3Q7RdNwBc12Irl+6sIAAd7i";
    static String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAslFEsSczHhDXpyNSR81GY9PlCSKTCcFw9pxULJyIi/dwABDK5GaYJRVtI4qncFS50qGtaCK4rSfYqbJ6S1NyG1pGK9e+dibuSJNOoTVKdpxzmPSrW+JTcGyzwlhO0X11xtQj4bjY2MDiDjOfOjFNn3UnDTFZRsElKbnxItOahS93GdjMvqZxIbiZeborVVRzJZjyCJ4DRq37ErIUOb2leCTAxIqlwLYTLry5KqvNh5XlJs9v9miFiWgVeZ+vd5y8leHgqriU7H6Hpz7HR9m5BmOjVlj7FMlIvlkcDIiI7KQlcBQuuuFcsE6XPgoITjavOVcc8caL/U/hCiE1LShfwQIDAQAB";
    static AlipayConfig alipayConfig = new AlipayConfig();
    static AlipayClient alipayClient;
    static {
        alipayConfig.setServerUrl("https://openapi-sandbox.dl.alipaydev.com/gateway.do");
        alipayConfig.setAppId("9021000134613169");
        alipayConfig.setPrivateKey(privateKey);
        alipayConfig.setFormat("json");
        alipayConfig.setAlipayPublicKey(alipayPublicKey);
        alipayConfig.setCharset("UTF-8");
        alipayConfig.setSignType("RSA2");
        try {
            alipayClient = new DefaultAlipayClient(alipayConfig);
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/getPage")
    public String getPayPage(String id) throws AlipayApiException {
        // 引用私钥
        // 设置支付宝公钥
        // 创建发生请求的对象

        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        AlipayTradePagePayModel model = new AlipayTradePagePayModel();

        model.setOutTradeNo(id);
        model.setTotalAmount("88.88");
        model.setSubject("Iphone6 16G");
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        // 设置支付的超时时间
        // 1. 绝对时间, 优先使用 格式为yyyy-MM-dd HH:mm:ss
        //model.setTimeExpire("2024-02-02 13:37:00");
        // 2. 相对时间
        model.setTimeoutExpress("1m");
        // 构建请求体
        request.setBizModel(model);
        // 设置回调地址,
        request.setReturnUrl("http://m.kiroe.cn:8101/pay/returnURL");
        // 设置异步回调
        request.setNotifyUrl("http://ali.kiroe.cn:8101/pay/notifyUrl");
        // 请求体
        AlipayTradePagePayResponse response = alipayClient.pageExecute(request, "POST");
        // 如果需要返回GET请求，请使用
        // AlipayTradePagePayResponse response = alipayClient.pageExecute(request, "GET");
        String pageRedirectionData = response.getBody();
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
            // sdk版本是"4.38.0.ALL"及以上,可以参考下面的示例获取诊断链接
            String diagnosisUrl = DiagnosisUtils.getDiagnosisUrl(response);
            System.out.println(diagnosisUrl);
        }
        return pageRedirectionData;

    }

    @GetMapping("/pay/returnURL")
    public String returnUrl(){
        return "同步回调成功";
    }

    /**
     * 异步回调
     * 在进行异步通知交互时，如果支付宝收到的应答不是 success ，支付宝会认为通知失败，会通过一定的策略定期重新发起通知。通知的间隔频率为：4m、10m、10m、1h、2h、6h、15h。
     * 商家设置的异步地址（notify_url）需保证无任何字符，如空格、HTML 标签，且不能重定向。（如果重定向，支付宝会收不到 success 字符，会被支付宝服务器判定为该页面程序运行出现异常，而重发处理结果通知）
     * 支付宝是用 POST 方式发送通知信息，商户获取参数的方式如下：request.Form("out_trade_no")、$_POST['out_trade_no']。
     * 支付宝针对同一条异步通知重试时，异步通知参数中的 notify_id 是不变的
     * @param
     * @return
     */
    @PostMapping("/pay/notifyUrl")
    public String returnNotifyUrl(@RequestParam Map<String, String> paramsMap) throws AlipayApiException {
        System.out.println("异步回调了");
        boolean signVerified = AlipaySignature.rsaCheckV1(paramsMap, alipayPublicKey,"utf-8" , "RSA2");//调用SDK验证签名
        log.info(String.valueOf(signVerified));
        System.out.println("paramsMap = " + paramsMap);
        // 1. 获取notify_id
        String notifyId = paramsMap.get("notify_id");
        // 2. 获取appid
        String appId = paramsMap.get("app_id");
        // 3. 获取订单交易流水号
        String outTradeNo = paramsMap.get("out_trade_no");
        // 4. 获取交易总金额
        String totalAmount = paramsMap.get("total_amount");
        // 5. 获取交易状态
        String tradeStatus = paramsMap.get("trade_status");
        log.info("{},{},{}, {},{}",notifyId,appId,outTradeNo,totalAmount,tradeStatus);
        // 继续验证业务参数
        // 1. 总金额
        // 2. appid
        // 3. 交易状态
        // 4. 验证outTradeNo
        return "success";
    }
    /**
     * 请求包括request( model)
     * 交易查询接口
     * 向支付宝服务器发起请求，查询用户的支付结果
     *
     * 在不同的场景下，会查询到什么样的结果
     * 1. 用户没有获取支付页面
     * 2. 用户获取了支付页面,但没有输入用户密码登录， 1和2返回相同
     * 3. 用户获取了支付页面，登录了用户，但没输入支付密码
     * 4. 页面，登录，支付密码，完成了交易
     * 5. 关闭了此次交易,
     * @param outTradeNo
     * @param payTypeName
     * @return
     */
    public String queryPaymentInfoByOutTradeNoAndPaymentType(final String outTradeNo, final String payTypeName) {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
        request.setBizModel(model);
        model.setOutTradeNo(outTradeNo);
        try {
            AlipayTradeQueryResponse response = alipayClient.execute(request);


        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    /**
     * 交易关闭接口
     * 1. 只能关闭支付宝那边已经存在的交易记录，而且必须为 WAIT_BUYER_PAY 状态，即登录了，但没输入支付密码的状态
     * @param outTradeNo
     * @return
     * @throws AlipayApiException
     */
    public String closePay(String outTradeNo) throws AlipayApiException {
        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
        AlipayTradeCloseModel model = new AlipayTradeCloseModel();
        model.setOutTradeNo(outTradeNo);
        request.setBizModel(model);
        AlipayTradeCloseResponse response = alipayClient.execute(request);
        System.out.println(response.getBody());

        return null;
    }

}
