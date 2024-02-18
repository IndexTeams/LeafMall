package cn.kiroe.mall.payment.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.kiroe.mall.common.result.Result;
import cn.kiroe.mall.order.constant.OrderStatus;
import cn.kiroe.mall.order.dto.OrderInfoDTO;
import cn.kiroe.mall.pay.api.constant.PaymentConstant;
import cn.kiroe.mall.pay.api.constant.PaymentStatus;
import cn.kiroe.mall.pay.api.constant.PaymentType;
import cn.kiroe.mall.pay.api.dto.PaymentInfoDTO;
import cn.kiroe.mall.payment.alipay.MallAlipayConfig;
import cn.kiroe.mall.payment.client.OrderApiClient;
import cn.kiroe.mall.payment.converter.PaymentInfoConverter;
import cn.kiroe.mall.payment.mapper.PaymentInfoMapper;
import cn.kiroe.mall.payment.model.PaymentInfo;
import cn.kiroe.mall.payment.service.PayService;
import cn.kiroe.mall.payment.vo.PaymentNotification;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Author Kiro
 * @Date 2024/02/02 09:45
 **/
@Service
@RequiredArgsConstructor
@Slf4j
public class PayServiceImpl implements PayService {
    private final AlipayClient alipayClient;
    private final MallAlipayConfig mallAlipayConfig;
    private final OrderApiClient orderApiClient;
    private final PaymentInfoConverter paymentInfoConverter;
    private final PaymentInfoMapper paymentInfoMapper;

    /**
     * 创建订单
     *
     * @param orderId
     * @return
     */
    @Override
    public String createAliPay(final Long orderId) {
        // 1. 远程调用订单服务,通过订单id获取订单记录
        // 获取： 订单id,订单outTradeNo,订单标题，订单金额
        OrderInfoDTO orderInfoDTO = orderApiClient.getOrderInfoDTO(orderId);
        // 2. 校验订单状态
        // 2.1 订单记录是否存在
        // 2.2 订单状态： UNPAID
        if (orderInfoDTO == null || !OrderStatus.UNPAID.name().equals(orderInfoDTO.getOrderStatus())) {
            throw new RuntimeException("订单不存在，或者订单已经支付");
        }
        // 保存订单
        savePaymentInfo(orderInfoDTO, PaymentType.ALIPAY.name());
        // 4. 向支付宝发起请求，获取支付页面
        // 支付宝超时时间:expire_time,从订单中的 expire_time获取
        AlipayTradePagePayResponse response = sendCreatePayPage(orderInfoDTO);
        if (response.isSuccess()) {
            log.info("获取的表单为:{}", response.getBody());
            return response.getBody();
        } else {
            log.error("请求支付宝页面错误");
            throw new RuntimeException("未获取支付宝页面");
        }
    }

    public AlipayTradePagePayResponse sendCreatePayPage(final OrderInfoDTO orderInfoDTO) {
        try {
            AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
            request.setReturnUrl(mallAlipayConfig.getReturnPaymentUrl());
            request.setNotifyUrl(mallAlipayConfig.getNotifyPaymentUrl());
            AlipayTradePagePayModel model = new AlipayTradePagePayModel();
            // 不可缺少的
            model.setOutTradeNo(orderInfoDTO.getOutTradeNo());
            model.setTotalAmount(orderInfoDTO.getTotalAmount().toString());
            model.setSubject(orderInfoDTO.getTradeBody());
            model.setProductCode(PaymentConstant.FAST_INSTANT_TRADE_PAY);
            model.setTimeExpire(DateUtil.formatDateTime(orderInfoDTO.getExpireTime()));
            request.setBizModel(model);
            return alipayClient.pageExecute(request, "POST");
        } catch (AlipayApiException e) {
            log.info("支付宝创建支付错误", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void savePaymentInfo(final OrderInfoDTO orderInfo, final String paymentTypeName) {
        // 3. 保存支付记录到本地支付表中
        PaymentInfo paymentInfo = paymentInfoConverter.contvertOrderInfoDTO2PaymentInfo(orderInfo);
        paymentInfo.setPaymentType(paymentTypeName);
        paymentInfo.setId(null);
        // 如果有了复用之前的
        LambdaQueryWrapper<PaymentInfo> queryWrapper = new LambdaQueryWrapper<PaymentInfo>()
                .eq(PaymentInfo::getOrderId, orderInfo.getId());
        boolean exists = paymentInfoMapper.exists(queryWrapper);
        if (exists) {// 已经存在复用之前的
            // paymentInfoMapper.delete(queryWrapper);
            return;
        }
        // 没有则插入
        paymentInfoMapper.insert(paymentInfo);
    }


    @Override
    public PaymentInfoDTO queryPaymentInfoByOutTradeNoAndPaymentType(final String outTradeNo, final String payTypeName) {
        PaymentInfo paymentInfo = paymentInfoMapper.selectOne(new LambdaQueryWrapper<PaymentInfo>().eq(PaymentInfo::getOutTradeNo, outTradeNo).eq(PaymentInfo::getPaymentType, payTypeName));
        return paymentInfoConverter.convertPaymentInfoToDTO(paymentInfo);
    }

    /**
     * 幂等性: 1^N = 1
     * 幂等性接口: 一个接口被调用若干次，返回的结果都要是一样的
     * 1. 在redis中添加被执行的标记
     * 2. 出问题了，删除redis中的幂等标记,setnx添加幂等标记，分布式锁
     *
     * @param paymentNotification
     * @return
     */
    @Override
    @Transactional
    public String callBackNotify(final PaymentNotification paymentNotification) {
        // 2. 验证业务参数
        // 2.1 获取订单信息
        PaymentInfoDTO paymentInfoDTO = queryPaymentInfoByOutTradeNoAndPaymentType(paymentNotification.getOutTradeNo(), PaymentType.ALIPAY.name());
        checkPayParam(paymentNotification, paymentInfoDTO);
        if (!checkPayParam(paymentNotification, paymentInfoDTO)) {
            log.info("验证参数失败");
            return "failure";
        }
        // 验证幂等,如果变为已经支付，则直接显示成功
        if (PaymentStatus.PAID.name().equals(paymentInfoDTO.getPaymentStatus())) {
            return "success";
        }
        // 3. 修改支付表状态
        if (!updatePayment(paymentInfoDTO.getId(), PaymentStatus.PAID, paymentInfoDTO.getTradeNo(),
                JSON.toJSONString(paymentInfoDTO), new Date())) {
            throw new RuntimeException("更新支付表失败");
        }
        // 4. 修改订单表的订单状态
        // 5. 并扣减库存, 在构建库存的过程中，可能会涉及到拆单
        Result result = orderApiClient.successPay(paymentInfoDTO.getOrderId());
        if (!result.isOk()) {
            throw new RuntimeException("修改订单表失败");
        }
        // 回调成功，返回success
        return "success";
    }

    @Override
    public PaymentInfoDTO getPaymentInfoDTOByOutTradeNo(final String outTradeNo) {
        PaymentInfo paymentInfo = paymentInfoMapper.selectOne(new LambdaQueryWrapper<PaymentInfo>()
                .eq(PaymentInfo::getOutTradeNo, outTradeNo));
        return paymentInfoConverter.convertPaymentInfoToDTO(paymentInfo);
    }

    @Override
    public boolean closePaymentInfo(final String outTradeNo) {
        int update = paymentInfoMapper.update(new LambdaUpdateWrapper<PaymentInfo>()
                .eq(PaymentInfo::getOutTradeNo, outTradeNo)
                .set(PaymentInfo::getPaymentStatus, PaymentStatus.CLOSED));
        return update == 1;
    }


    private boolean updatePayment(final Long id, final PaymentStatus paymentStatus, final String tradeNo, final String callBackContent, final Date callBackTime) {
        int update = paymentInfoMapper.update(
                new LambdaUpdateWrapper<PaymentInfo>()
                        .set(PaymentInfo::getPaymentStatus, paymentStatus.name())
                        .set(PaymentInfo::getTradeNo, tradeNo)
                        .set(PaymentInfo::getCallbackContent, callBackContent)
                        .set(PaymentInfo::getCallbackTime, callBackTime)
                        .eq(PaymentInfo::getId, id));
        return update == 1;
    }

    private boolean checkPayParam(final PaymentNotification paymentNotification, final PaymentInfoDTO paymentInfoDTO) {
        return paymentNotification.getTotalAmount().equals(paymentInfoDTO.getTotalAmount().toString())
                && paymentNotification.getAppId().equals(mallAlipayConfig.getAppId())
                && paymentNotification.getTradeStatus().equals(PaymentConstant.TRADE_SUCCESS)
                && paymentNotification.getOutTradeNo().equals(paymentInfoDTO.getOutTradeNo());
    }
}
