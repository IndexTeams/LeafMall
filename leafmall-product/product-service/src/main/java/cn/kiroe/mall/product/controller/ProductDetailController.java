package cn.kiroe.mall.product.controller;

import cn.kiroe.mall.common.result.Result;
import cn.kiroe.mall.log.annotation.Log;
import cn.kiroe.mall.product.client.SearchApiClient;
import cn.kiroe.mall.product.dto.ProductDetailDTO;
import cn.kiroe.mall.product.service.ProductDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Kiro
 * @Date 2024/01/22 09:39
 **/
@RestController
@RequiredArgsConstructor
public class ProductDetailController {
    private final ProductDetailService productDetailService;
    private final SearchApiClient searchApiClient;
    @Log
    @GetMapping("goods/{skuId}")
    public Result<ProductDetailDTO> getItem(@PathVariable Long skuId){
        // 获取商品详情页的数据
        ProductDetailDTO itemBySkuId = productDetailService.getItemBySkuId(skuId);
        // 增加热度
        searchApiClient.incrHotScore(skuId);
        return Result.ok(itemBySkuId);
    }

}
