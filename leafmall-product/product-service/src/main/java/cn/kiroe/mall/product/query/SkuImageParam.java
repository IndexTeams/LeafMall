package cn.kiroe.mall.product.query;

import cn.kiroe.mall.product.model.SkuImage;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

@Data
@AutoMapper(target = SkuImage.class)
public class SkuImageParam {

    //"id"
    private Long id;

    //"商品id"
    private Long skuId;

    //"图片名称（冗余）"
    private String imgName;

    //"图片路径(冗余)"
    private String imgUrl;

    //"商品图片id"
    private Long spuImgId;

    //"是否默认"
    private String isDefault;
}
