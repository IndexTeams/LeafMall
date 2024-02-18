package cn.kiroe.mall.cart.controller;

import cn.hutool.core.util.StrUtil;
import cn.kiroe.mall.cart.api.dto.CartInfoDTO;
import cn.kiroe.mall.cart.service.CartService;
import cn.kiroe.mall.common.result.Result;
import cn.kiroe.mall.common.util.AuthContext;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Kiro
 * @Date 2024/01/30 10:24
 **/
@RestController
public class CartController {
    @Autowired
    CartService cartService;

    // 添加
    @RequestMapping("cart/add/{skuId}/{skuNum}")
    public Result addCart(@PathVariable Long skuId, @PathVariable Integer skuNum, HttpServletRequest request) {

        // 获取用户id
        String userId = AuthContext.getUserId(request);
        if (StrUtil.isBlank(userId)) {// 获取临时用户id
            userId = AuthContext.getUserTempId(request);
        }
        // 添加商品到购物车
        cartService.addToCart(skuId, userId, skuNum);

        return Result.ok();
    }

    // 更新选中状态
    @PutMapping("cart/check/{skuId}/{isChecked}")
    public Result checkCart(@PathVariable Long skuId, @PathVariable Integer isChecked,
                            HttpServletRequest request) {
        // 修改指定用户购物车中指定商品的 isChecked状态
        // 获取用户id
        String userId = AuthContext.getUserId(request);
        if (StrUtil.isBlank(userId)) {// 获取临时用户id
            userId = AuthContext.getUserTempId(request);
        }
        cartService.checkCart(userId,isChecked,skuId);
        return Result.ok();
    }

    // 查看
    @GetMapping("/cart")
    public Result cartList(HttpServletRequest request) {
        // 获取用户Id
        String userId = AuthContext.getUserId(request);
        // 获取临时用户Id
        String userTempId = AuthContext.getUserTempId(request);
        // 调用购物车服务，获取用户的购物车列表
        // 如果userId不为空且userTempId也不为空，要合并购物车，并删除临时用户的购物车
        List<CartInfoDTO> cartInfoList = cartService.getCartList(userId, userTempId);
        return Result.ok(cartInfoList);
    }

    // 删除
    @DeleteMapping("/cart/{skuId}")
    public Result deleteCart(@PathVariable("skuId") Long skuId, HttpServletRequest request) {
        // 注意在获取用户购物车数据时，使用和添加购物车时的key相同
        // 删除指定用户购物车中指定的商品
        // 获取用户id
        String userId = AuthContext.getUserId(request);
        if (StrUtil.isBlank(userId)) {// 获取临时用户id
            userId = AuthContext.getUserTempId(request);
        }
        cartService.deleteCart(skuId,userId);
        return Result.ok();

    }

    // 删除所有被选中的
    @DeleteMapping("/cart/checked")
    public Result deleteChecked(HttpServletRequest request) {
        // 注意在获取用户购物车数据时，使用和添加购物车时的key相同
        // 根据userId先获取购物车数据
        String userId = AuthContext.getUserId(request);
        if (StrUtil.isBlank(userId)) {// 获取临时用户id
            userId = AuthContext.getUserTempId(request);
        }
        // 遍历购物车中的购物车商品数据，删除用户购物车中所有已经被选中的商品
        cartService.deleteChecked(userId);
        return Result.ok();
    }
}
