package cn.kiroe.mall.product.converter.dto;



import cn.kiroe.mall.product.dto.SaleAttributeInfoDTO;
import cn.kiroe.mall.product.model.SaleAttributeInfo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel =  MappingConstants.ComponentModel.SPRING )
public interface SaleAttributeInfoConverter {

    SaleAttributeInfoDTO saleAttributeInfoPO2DTO(SaleAttributeInfo saleAttributeInfo);
    List<SaleAttributeInfoDTO> saleAttributeInfoPOs2DTOs(List<SaleAttributeInfo> saleAttributeInfos);
}
