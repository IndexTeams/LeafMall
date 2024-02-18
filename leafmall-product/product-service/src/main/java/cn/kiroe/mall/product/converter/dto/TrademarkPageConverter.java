package cn.kiroe.mall.product.converter.dto;

import com.baomidou.mybatisplus.core.metadata.IPage;
import cn.kiroe.mall.product.dto.TrademarkPageDTO;
import cn.kiroe.mall.product.model.Trademark;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel =  MappingConstants.ComponentModel.SPRING , uses = TrademarkConverter.class)
public interface TrademarkPageConverter {

    TrademarkPageDTO tradeMarkPagePO2PageDTO(IPage<Trademark> trademarkPage);
}
