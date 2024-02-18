package cn.kiroe.mall.product.converter.dto;


import cn.kiroe.mall.product.dto.PlatformAttributeInfoDTO;
import cn.kiroe.mall.product.dto.PlatformAttributeValueDTO;
import cn.kiroe.mall.product.model.PlatformAttributeInfo;
import cn.kiroe.mall.product.model.PlatformAttributeValue;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel =  MappingConstants.ComponentModel.SPRING )
public interface PlatformAttributeInfoConverter {

    PlatformAttributeInfoDTO platformAttributeInfoPO2DTO(PlatformAttributeInfo platformAttributeInfo);
    List<PlatformAttributeInfoDTO> platformAttributeInfoPOs2DTOs(List<PlatformAttributeInfo> platformAttributeInfos);

    PlatformAttributeValueDTO platformAttributeValuePO2DTO(PlatformAttributeValue platformAttributeValue);

}
