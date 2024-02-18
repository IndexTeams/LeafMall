package cn.kiroe.mall.product.converter.param;


import cn.kiroe.mall.product.dto.SpuPosterDTO;
import cn.kiroe.mall.product.model.*;
import cn.kiroe.mall.product.query.SpuImageParam;
import cn.kiroe.mall.product.query.SpuInfoParam;
import cn.kiroe.mall.product.query.SpuSaleAttributeInfoParam;
import cn.kiroe.mall.product.query.SpuSaleAttributeValueParam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel =  MappingConstants.ComponentModel.SPRING )
public interface SpuInfoParamConverter {


    @Mapping(source = "category3Id", target = "thirdLevelCategoryId")
    @Mapping(source = "spuSaleAttrList", target = "spuSaleAttributeInfoList")
    SpuInfo spuInfoParam2Info(SpuInfoParam spuInfo);


    SpuPosterDTO spuPosterPO2DTO(SpuPoster spuPoster);
    List<SpuPosterDTO> spuPosterPOs2DTOs(List<SpuPoster> spuPosters);

    SpuImage spuImageParam2Image(SpuImageParam spuImage);
    List<SpuImage> spuImageParams2Images(List<SpuImageParam> spuImages);
    @Mapping(source = "baseSaleAttrId", target = "saleAttrId")
    SpuSaleAttributeInfo spuSaleAttributeParam2Info(SpuSaleAttributeInfoParam spuSaleAttributeInfo);
    List<SpuSaleAttributeInfo> spuSaleAttributeParams2Infos(List<SpuSaleAttributeInfoParam> spuSaleAttributeInfos);
    @Mapping(source = "saleAttrValueName", target = "spuSaleAttrValueName")
    @Mapping(source = "baseSaleAttrId", target = "spuSaleAttrId")
    SpuSaleAttributeValue spuSaleAttributeValueParam2Value(SpuSaleAttributeValueParam spuSaleAttributeValue);
    List<SpuSaleAttributeValue> spuSaleAttributeValueParams2Values(List<SpuSaleAttributeValueParam> spuSaleAttributeValues);
}
