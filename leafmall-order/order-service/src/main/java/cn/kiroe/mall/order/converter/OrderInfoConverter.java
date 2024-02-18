package cn.kiroe.mall.order.converter;

import cn.kiroe.mall.order.dto.OrderDetailDTO;
import cn.kiroe.mall.order.dto.OrderInfoDTO;
import cn.kiroe.mall.order.model.OrderDetail;
import cn.kiroe.mall.order.model.OrderInfo;
import cn.kiroe.mall.order.query.OrderDetailParam;
import cn.kiroe.mall.order.query.OrderInfoParam;
import cn.kiroe.mall.ware.api.dto.WareOrderTaskDTO;
import cn.kiroe.mall.ware.api.dto.WareOrderTaskDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderInfoConverter {

    OrderInfoDTO convertOrderInfoToOrderInfoDTO(OrderInfo orderInfo);

    OrderInfo convertOrderInfoParam(OrderInfoParam orderInfoParam);

    List<OrderInfoDTO> POListtoDTOList(List<OrderInfo> orderInfoList);

    OrderDetail convertOrderDetailParam(OrderDetailParam orderDetailParam);

    OrderDetailDTO detail2DTO(OrderDetail orderDetail);

    @Mapping(source = "id",target = "orderId")
    @Mapping(source = "tradeBody",target = "orderBody" )
    @Mapping(source = "orderDetailList",target = "details")
    WareOrderTaskDTO convertOrderInfoToWareOrderTaskDTO(OrderInfo orderInfo);

    WareOrderTaskDetailDTO convertDetail(OrderDetail orderDetail);

    OrderInfo copyOrderInfo(OrderInfoDTO orderInfo);



}
