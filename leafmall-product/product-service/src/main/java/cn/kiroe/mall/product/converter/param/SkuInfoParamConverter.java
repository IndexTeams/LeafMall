package cn.kiroe.mall.product.converter.param;


import cn.kiroe.mall.product.model.SkuImage;
import cn.kiroe.mall.product.model.SkuInfo;
import cn.kiroe.mall.product.model.SkuPlatformAttributeValue;
import cn.kiroe.mall.product.model.SkuSaleAttributeValue;
import cn.kiroe.mall.product.query.SkuImageParam;
import cn.kiroe.mall.product.query.SkuInfoParam;
import cn.kiroe.mall.product.query.SkuPlatformAttributeValueParam;
import cn.kiroe.mall.product.query.SkuSaleAttributeValueParam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel =  MappingConstants.ComponentModel.SPRING )
public interface SkuInfoParamConverter {

    @Mapping(source = "category3Id", target = "thirdLevelCategoryId")
    @Mapping(source = "skuAttrValueList", target = "skuPlatformAttributeValueList")
    @Mapping(source = "skuSaleAttrValueList", target = "skuSaleAttributeValueList")
    SkuInfo SkuInfoParam2Info(SkuInfoParam skuInfoParam);

    SkuImage skuImageParam2Image(SkuImageParam skuImageParam);

    SkuPlatformAttributeValue skuPlatformAttributeValueParam2Value(SkuPlatformAttributeValueParam param);

    @Mapping(source = "saleAttrValueId", target = "spuSaleAttrValueId")
    SkuSaleAttributeValue skuSaleAttributeValueParam2Value(SkuSaleAttributeValueParam param);

}
