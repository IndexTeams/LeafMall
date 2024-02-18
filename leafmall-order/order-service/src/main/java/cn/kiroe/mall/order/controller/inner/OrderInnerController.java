package cn.kiroe.mall.order.controller.inner;

import cn.kiroe.mall.common.result.Result;
import cn.kiroe.mall.order.constant.OrderStatus;
import cn.kiroe.mall.order.constant.OrderType;
import cn.kiroe.mall.order.converter.OrderInfoConverter;
import cn.kiroe.mall.order.dto.OrderInfoDTO;
import cn.kiroe.mall.order.model.OrderDetail;
import cn.kiroe.mall.order.model.OrderInfo;
import cn.kiroe.mall.order.query.OrderDetailParam;
import cn.kiroe.mall.order.query.OrderInfoParam;
import cn.kiroe.mall.order.service.OrderService;
import cn.kiroe.mall.ware.api.dto.WareOrderTaskDTO;
import cn.kiroe.mall.ware.api.dto.WareSkuDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * @Author Kiro
 * @Date 2024/02/02 09:49
 **/
@RestController
@RequiredArgsConstructor
public class OrderInnerController {
    private final OrderService orderService;
    private final OrderInfoConverter orderInfoConverter;

    // 通过订单id获取订单信息
    @GetMapping("/api/order/inner/getOrderInfo/{orderId}")
    public OrderInfoDTO getOrderInfoDTO(@PathVariable(value = "orderId") Long orderId){
        return orderService.getOrderInfo(orderId);
    }


    // 支付成功，修改订单状态
    @PostMapping("/api/order/inner/success/{orderId}")
    Result successPay(@PathVariable(value = "orderId") Long orderId){
        orderService.successPay(orderId);
        return Result.ok();
    }

    /**
     * 拆单
     */
    @PostMapping("/api/order/inner/orderSplit/{orderId}")
    List<WareOrderTaskDTO> orderSplit(@PathVariable(value = "orderId") String orderId, @RequestBody List<WareSkuDTO> wareSkuDTOList){
        return orderService.orderSplit(orderId,wareSkuDTOList);
    }

    /**
     * 库存扣减完成，修改订单状态
     */
    @PostMapping("/api/order/inner/successLockStock/{orderId}/{taskStatus}")
    Result successLockStock(@PathVariable(value = "orderId") String orderId, @PathVariable(value = "taskStatus") String taskStatus){
        orderService.successLockStock(orderId,taskStatus);
        return Result.ok();
    }

    @PostMapping("/api/order/inner/seckill/submitOrder")
    public Long submitOrder(@RequestBody OrderInfoParam orderInfoParam) {
        for (final OrderDetailParam orderDetailParam : orderInfoParam.getOrderDetailList()) {
            orderDetailParam.setId(null);
        }
        // 转化OrderInfoParam为 OrderInfo
        OrderInfo orderInfo = orderInfoConverter.convertOrderInfoParam(orderInfoParam);
        // 设置订单类型为秒杀订单
        orderInfo.sumTotalAmount();
        orderInfo.setOrderStatus(OrderStatus.UNPAID.name());
        orderInfo.setOrderType(OrderType.PROMO_ORDER.name());
        orderInfo.setOutTradeNo(UUID.randomUUID().toString().replace("-",""));
        orderInfo.setTradeBody(orderInfo.getOrderDetailList().stream().map(OrderDetail::getSkuName).toList().toString());
        // 保存订单以及订单详情 到数据库
        Long orderId = orderService.saveOrderInfo(orderInfo);
        return orderId;
    }

}
