package cn.kiroe.mall.promo.converter;

import cn.kiroe.mall.order.dto.OrderDetailDTO;
import cn.kiroe.mall.order.dto.OrderInfoDTO;
import cn.kiroe.mall.promo.api.dto.SeckillGoodsDTO;
import cn.kiroe.mall.promo.model.SeckillGoods;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SeckillGoodsConverter {

    SeckillGoodsDTO convertSeckillGoodsToDTO(SeckillGoods seckillGoods);

    List<SeckillGoodsDTO> convertSeckillGoodsList(List<SeckillGoods> seckillGoodsList);

    SeckillGoods convertSeckillDTO(SeckillGoodsDTO seckillGoods);
    @Mapping(source = "seckillGoods.skuDefaultImg", target = "imgUrl")
    @Mapping(source = "seckillGoods.costPrice", target = "orderPrice")
    @Mapping(source = "num", target = "skuNum")
    OrderDetailDTO secondKillGoodsToOrderDetailDTO(SeckillGoods seckillGoods, Integer num);
}
