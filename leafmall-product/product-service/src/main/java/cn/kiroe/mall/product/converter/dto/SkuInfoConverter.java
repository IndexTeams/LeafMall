package cn.kiroe.mall.product.converter.dto;



import cn.kiroe.mall.product.dto.SkuImageDTO;
import cn.kiroe.mall.product.dto.SkuInfoDTO;
import cn.kiroe.mall.product.dto.SkuPlatformAttributeValueDTO;
import cn.kiroe.mall.product.dto.SkuSaleAttributeValueDTO;
import cn.kiroe.mall.product.model.SkuImage;
import cn.kiroe.mall.product.model.SkuInfo;
import cn.kiroe.mall.product.model.SkuPlatformAttributeValue;
import cn.kiroe.mall.product.model.SkuSaleAttributeValue;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel =  MappingConstants.ComponentModel.SPRING )
public interface SkuInfoConverter {

    SkuInfoDTO skuInfoPO2DTO(SkuInfo skuInfo);

    SkuImageDTO skuImagePO2DTO(SkuImage skuImage);

    SkuPlatformAttributeValueDTO skuPlatformAttributeValuePO2DTO(
            SkuPlatformAttributeValue skuPlatformAttributeValue);

    SkuSaleAttributeValueDTO skuSaleAttributeValuePOs2DTOs(
            SkuSaleAttributeValue skuSaleAttributeValue);
}
