package cn.kiroe.mall.pay.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Kiro
 * @Date 2024/02/03 15:36
 **/
@NoArgsConstructor
@Data
public class AliQueryResponse {

    @JsonProperty("alipay_trade_query_response")
    private AlipayTradeQueryResponseDTO alipayTradeQueryResponse;
    @JsonProperty("sign")
    private String sign;


}
