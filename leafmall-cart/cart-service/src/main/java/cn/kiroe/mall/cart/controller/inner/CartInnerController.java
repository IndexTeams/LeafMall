package cn.kiroe.mall.cart.controller.inner;

import cn.kiroe.mall.cart.api.dto.CartInfoDTO;
import cn.kiroe.mall.cart.service.CartService;
import cn.kiroe.mall.common.result.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Kiro
 * @Date 2024/02/01 10:50
 **/

@RestController
@RequiredArgsConstructor
public class CartInnerController {
    private final CartService cartService;

    // 下单的时候 查询购物车中所有被选中的商品
    @GetMapping("/api/cart/inner/getCartCheckedList/{userId}")
    public List<CartInfoDTO> getCartCheckedList(@PathVariable(value = "userId") String userId){
        return cartService.getCartCheckedList(userId);
    }

    @PutMapping("/api/cart/inner/delete/order/cart/{userId}")
    public Result removeCartProductsInOrder(@PathVariable("userId") String userId, @RequestBody List<Long> skuIds){
        for (final Long skuId : skuIds) {
            cartService.deleteCart(skuId,userId);
        }
        return Result.ok();
    }

    @GetMapping("/api/cart/inner/refresh/{userId}/{skuId}")
    public Result refreshCartPrice(@PathVariable(value = "userId") String userId, @PathVariable(value = "skuId") Long skuId){
        cartService.refreshCartPrice(userId,skuId);
        return Result.ok();
    }
}
