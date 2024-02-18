package cn.kiroe.mall.product.dto;

import lombok.Data;

@Data
public class SpuSaleAttributeValueDTO {

    private Long id;

    //"商品id"
    private Long spuId;

    //"销售属性id"
    private Long spuSaleAttrId;

    //"销售属性值名称"
    private String spuSaleAttrValueName;

    //"是否关联到了sku的属性值", 1.表示选中 ， 0表示未选中
    String isChecked;
}
