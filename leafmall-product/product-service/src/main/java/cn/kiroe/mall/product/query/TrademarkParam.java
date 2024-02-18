package cn.kiroe.mall.product.query;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class TrademarkParam {
    // "品牌id"
    private Long id;
    @NotEmpty
    // "属性值"
    private String tmName;
    // "品牌logo的图片路径"
    private String logoUrl;
}
