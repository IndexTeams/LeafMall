package cn.kiroe.mall.product.converter.dto;

import cn.kiroe.mall.product.dto.*;
import cn.kiroe.mall.product.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel =  MappingConstants.ComponentModel.SPRING )
public interface SpuInfoConverter {

    SpuInfoDTO spuInfoPO2DTO(SpuInfo spuInfo);

    SpuSaleAttributeInfoDTO spuSaleAttributeInfoPO2DTO(SpuSaleAttributeInfo spuSaleAttributeInfo);
    List<SpuSaleAttributeInfoDTO> spuSaleAttributeInfoPOs2DTOs(List<SpuSaleAttributeInfo> spuSaleAttributeInfos);

    SpuSaleAttributeValueDTO spuSaleAttributeValuePO2DTO(SpuSaleAttributeValue spuSaleAttributeValue);
    List<SpuSaleAttributeValueDTO> spuSaleAttributeValuePOs2DTOs(List<SpuSaleAttributeValue> spuSaleAttributeValues);

    SpuImageDTO spuImagePO2spuImageDTO(SpuImage spuImage);
    List<SpuImageDTO> spuImagePOs2DTOs(List<SpuImage> spuImages);

    SpuPosterDTO spuPosterPO2DTO(SpuPoster spuPoster);
    List<SpuPosterDTO> spuPosterPOs2DTOs(List<SpuPoster> spuPosters);
}
