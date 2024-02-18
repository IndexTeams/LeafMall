package cn.kiroe.mall.search.converter;

import cn.kiroe.mall.product.dto.SkuInfoDTO;
import cn.kiroe.mall.search.model.Goods;
import cn.kiroe.mall.search.model.SearchAttr;
import cn.kiroe.mall.search.dto.GoodsDTO;
import cn.kiroe.mall.search.dto.SearchAttrDTO;
import org.mapstruct.Mapper;


import java.util.List;

@Mapper(componentModel = "spring")
public interface GoodsConverter {

    GoodsDTO goodsPO2DTO(Goods goods);

    Goods skuInfoDTO2GoodsPO(SkuInfoDTO skuInfoDTO);

    List<GoodsDTO> goodsPOs2DTOs(List<Goods> goods);

    SearchAttrDTO searchAttrPO2DTO(SearchAttr searchAttr);


}
