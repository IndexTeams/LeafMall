package cn.kiroe.mall.product.converter.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kiroe.mall.product.dto.SkuInfoPageDTO;
import cn.kiroe.mall.product.model.SkuInfo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel =  MappingConstants.ComponentModel.SPRING , uses = SkuInfoConverter.class)
public interface SkuInfoPageConverter {

    SkuInfoPageDTO skuInfoPagePO2PageDTO(Page<SkuInfo> skuInfoPage);

}
