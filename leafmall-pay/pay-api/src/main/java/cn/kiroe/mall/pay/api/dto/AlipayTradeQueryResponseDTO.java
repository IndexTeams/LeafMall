package cn.kiroe.mall.pay.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AlipayTradeQueryResponseDTO {
    @JsonProperty("code")
    private String code;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("buyer_logon_id")
    private String buyerLogonId;
    @JsonProperty("buyer_pay_amount")
    private String buyerPayAmount;
    @JsonProperty("buyer_user_id")
    private String buyerUserId;
    @JsonProperty("buyer_user_type")
    private String buyerUserType;
    @JsonProperty("invoice_amount")
    private String invoiceAmount;
    @JsonProperty("out_trade_no")
    private String outTradeNo;
    @JsonProperty("point_amount")
    private String pointAmount;
    @JsonProperty("receipt_amount")
    private String receiptAmount;
    @JsonProperty("send_pay_date")
    private String sendPayDate;
    @JsonProperty("total_amount")
    private String totalAmount;
    @JsonProperty("trade_no")
    private String tradeNo;
    @JsonProperty("trade_status")
    private String tradeStatus;
}