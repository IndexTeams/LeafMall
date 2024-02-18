package cn.kiroe.mall.product.converter.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kiroe.mall.product.dto.SpuInfoPageDTO;
import cn.kiroe.mall.product.model.SpuInfo;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel =  MappingConstants.ComponentModel.SPRING , uses = SpuInfoConverter.class)
public interface SpuInfoPageConverter {

    SpuInfoPageDTO spuInfoPage2PageDTO(Page<SpuInfo> SpuInfoPage);

}
