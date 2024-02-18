package cn.kiroe.mall.payment.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
@Data
public class PaymentNotification {

    @JsonProperty("voucher_detail_list")
    private List<VoucherDetail> voucherDetailList;

    @JsonProperty("fund_bill_list")
    private List<FundBill> fundBillList;

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("trade_no")
    private String tradeNo;

    @JsonProperty("gmt_create")
    private String gmtCreate;

    @JsonProperty("notify_type")
    private String notifyType;

    @JsonProperty("total_amount")
    private String totalAmount;

    @JsonProperty("out_trade_no")
    private String outTradeNo;

    @JsonProperty("invoice_amount")
    private String invoiceAmount;

    @JsonProperty("seller_id")
    private String sellerId;

    @JsonProperty("notify_time")
    private String notifyTime;

    @JsonProperty("trade_status")
    private String tradeStatus;

    @JsonProperty("gmt_payment")
    private String gmtPayment;

    @JsonProperty("receipt_amount")
    private String receiptAmount;

    @JsonProperty("passback_params")
    private String passbackParams;

    @JsonProperty("buyer_id")
    private String buyerId;

    @JsonProperty("app_id")
    private String appId;

    @JsonProperty("notify_id")
    private String notifyId;

    @JsonProperty("sign_type")
    private String signType;

    @JsonProperty("buyer_pay_amount")
    private String buyerPayAmount;

    @JsonProperty("sign")
    private String sign;

    @JsonProperty("point_amount")
    private String pointAmount;

    // Getters and Setters

    // Nested class for VoucherDetail
    public static class VoucherDetail {

        @JsonProperty("amount")
        private String amount;

        @JsonProperty("merchantContribute")
        private String merchantContribute;

        @JsonProperty("name")
        private String name;

        @JsonProperty("otherContribute")
        private String otherContribute;

        @JsonProperty("type")
        private String type;

        @JsonProperty("voucherId")
        private String voucherId;

        // Getters and Setters
    }

    // Nested class for FundBill
    public static class FundBill {

        @JsonProperty("amount")
        private String amount;

        @JsonProperty("fundChannel")
        private String fundChannel;

        // Getters and Setters
    }
}