package cn.kiroe.mall.product.converter.dto;

import cn.kiroe.mall.product.dto.TrademarkDTO;
import cn.kiroe.mall.product.model.Trademark;
import cn.kiroe.mall.product.query.TrademarkParam;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel =  MappingConstants.ComponentModel.SPRING )
public interface TrademarkConverter {

    TrademarkDTO trademarkPO2DTO(Trademark trademark);

    List<TrademarkDTO> trademarkPOs2DTOs(List<Trademark> trademarks);

    Trademark trademarkParam2Trademark(TrademarkParam trademarkParam);

}
