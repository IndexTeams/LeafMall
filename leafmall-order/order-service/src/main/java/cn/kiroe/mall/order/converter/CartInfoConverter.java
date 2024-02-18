package cn.kiroe.mall.order.converter;

import cn.kiroe.mall.cart.api.dto.CartInfoDTO;
import cn.kiroe.mall.order.dto.OrderDetailDTO;
import cn.kiroe.mall.order.model.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * 创建日期: 2023/03/16 09:56
 *
 * @author ciggar
 */
@Mapper(componentModel = "spring")
public interface CartInfoConverter {

    // 把购物车信息转化为订单详情
    @Mapping(source = "skuPrice",target = "orderPrice")
    OrderDetailDTO convertCartInfoDTOToOrderDetailDTO(CartInfoDTO cartInfoDTO);

    List<OrderDetailDTO> convertCartInfoDTOToOrderDetailDTOList(List<CartInfoDTO> cartInfoDTOs);
}
