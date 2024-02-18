package cn.kiroe.mall.product.converter.dto;

import cn.kiroe.mall.product.dto.PlatformAttributeValueDTO;
import cn.kiroe.mall.product.model.PlatformAttributeValue;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

/**
 * @Author Kiro
 * @Date 2024/01/18 17:47
 **/
@Mapper(componentModel =  MappingConstants.ComponentModel.SPRING )

public interface PlatformAttributeValueConverter {
    List<PlatformAttributeValueDTO> ValuesPO2DTOs(List<PlatformAttributeValue> platformAttributeValue);

}
