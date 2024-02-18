package cn.kiroe.mall.order.controller;

import cn.kiroe.mall.common.result.Result;
import cn.kiroe.mall.common.util.AuthContext;
import cn.kiroe.mall.order.dto.OrderInfoDTO;
import cn.kiroe.mall.order.dto.OrderTradeDTO;
import cn.kiroe.mall.order.query.OrderInfoParam;
import cn.kiroe.mall.order.service.OrderService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Kiro
 * @Date 2024/02/01 10:16
 **/
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * 获取订单结算界面
     *
     * @param request 前端中cookie或者header中有 token
     *                gateway将redis中的userID放在请求头中
     *                网关中有 Path: /order/* *
     * @return
     */
    @GetMapping("/order/auth/trade")
    public Result<OrderTradeDTO> trade(HttpServletRequest request) {
        String userId = AuthContext.getUserId(request);
        // 获取订单确认页面信息
        OrderTradeDTO trade = orderService.trade(userId);
        return Result.ok(trade);
    }

    @PostMapping("/order/auth/submitOrder")
    public Result submitOrder(@RequestBody OrderInfoParam orderInfoParam, HttpServletRequest request) {
        // 获取到用户Id
        Long userId = Long.valueOf(AuthContext.getUserId(request));
        Long orderId = orderService.submitOrder(orderInfoParam, userId);
        return Result.ok(orderId);
    }

    @GetMapping("/order/auth/{page}/{limit}")
    public Result<IPage<OrderInfoDTO>> index(@PathVariable Long page, @PathVariable Long limit, HttpServletRequest request) {
        // 获取到用户Id
        String userId = AuthContext.getUserId(request);
        Page<OrderInfoDTO> pageParam = new Page<>(page, limit);
        // 获取用户订单的分页列表
        // 注意：
        // 1. 建议使用MybatisPlus的分页查询功能
        // 2. 思考一下哪些订单状态不用在用户页面展示出来？
        // 3. 查询结果需要设置OrderInfoDTO中的orderStatusName(如'未支付'| '已支付' | '已发货')
        IPage<OrderInfoDTO> pageModel = orderService.getPage(pageParam, userId);
        return Result.ok(pageModel);
    }

}
