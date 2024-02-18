package cn.kiroe.mall.order.converter;

import cn.kiroe.mall.order.dto.OrderDetailDTO;
import cn.kiroe.mall.order.model.OrderDetail;
import org.mapstruct.Mapper;

/**
 * 创建日期: 2023/03/16 17:37
 *
 * @author ciggar
 */
@Mapper(componentModel = "spring")
public interface OrderDetailConverter {

    OrderDetailDTO convertOrderDetailDTO2PO(OrderDetail orderDetail);

    OrderDetail convertOrderDetailDTO2PO(OrderDetailDTO detailDTO);
}
