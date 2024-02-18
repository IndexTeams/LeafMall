package cn.kiroe.mall.ware.converter;

import cn.kiroe.mall.order.dto.OrderDetailDTO;
import cn.kiroe.mall.order.dto.OrderInfoDTO;
import cn.kiroe.mall.ware.api.dto.WareOrderTaskDTO;
import cn.kiroe.mall.ware.api.dto.WareOrderTaskDetailDTO;
import cn.kiroe.mall.ware.model.WareOrderTask;
import cn.kiroe.mall.ware.model.WareOrderTaskDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WareOrderTaskConverter {

    @Mapping(source = "id",target = "orderId")
    @Mapping(source = "tradeBody",target = "orderBody" )
    @Mapping(source = "orderDetailList",target = "details")
    WareOrderTask convertOrderInfoDTO(OrderInfoDTO orderInfoDTO);

    WareOrderTaskDetail convertOrderDetailDTO(OrderDetailDTO orderDetailDTO);

    WareOrderTask converWareOrderTask(WareOrderTaskDTO wareOrderTaskDTO);

    WareOrderTaskDetail convertOrderTaskDetailDTO(WareOrderTaskDetailDTO wareOrderTaskDetailDTO);

    List<WareOrderTask> convertWareOrderTaskDTO(List<WareOrderTaskDTO> wareOrderTaskDTOS);


}
