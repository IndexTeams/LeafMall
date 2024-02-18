package cn.kiroe.mall.product.model;

import lombok.Data;

//"详情页中的sku商品的一种销售属性组合"
@Data
public class SkuSaleAttributeValuePermutation {
    // 3770 | 3771
    //"销售属性一种取值组合的拼接字符串"
    String SkuSaleAttrValuePermutation;
    //"sku商品id"
    Long skuId;
}
