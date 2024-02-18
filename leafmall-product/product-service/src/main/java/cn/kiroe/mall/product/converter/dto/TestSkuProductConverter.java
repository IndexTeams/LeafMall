package cn.kiroe.mall.product.converter.dto;

import cn.kiroe.mall.product.dto.TestSkuProductDTO;
import cn.kiroe.mall.product.model.SkuInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

/**
 * Created by 北海 on 2023-06-05 16:06
 */
@Mapper(componentModel =  MappingConstants.ComponentModel.SPRING )
public interface TestSkuProductConverter {

    // SkuInfo ——> TestSkuProductDTO
    @Mapping(source = "skuName", target = "productName")
    @Mapping(source = "skuDefaultImg", target = "url")
    TestSkuProductDTO skuInfo2TestSkuProductDTO(SkuInfo skuInfo);

}
