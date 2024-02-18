package cn.kiroe.mall.product.controller;

import cn.kiroe.mall.product.dto.SkuInfoPageDTO;
import cn.kiroe.mall.common.result.Result;
import cn.kiroe.mall.product.model.SkuInfo;
import cn.kiroe.mall.product.query.SkuInfoParam;
import cn.kiroe.mall.product.service.SkuService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Kiro
 * @Date 2024/01/19 16:06
 **/
@RestController
@RequiredArgsConstructor
public class SkuController {
    private final SkuService skuService;
    //private final SearchApiClient searchApiClient;
    @PostMapping("admin/product/saveSkuInfo")
    public Result saveSkuInfo(@RequestBody SkuInfoParam skuInfo) {
        // 调用服务层
        skuService .saveSkuInfo(skuInfo);
        return Result.ok();
    }
    @GetMapping("admin/product/list/{page}/{limit}")
    public Result index(
            @PathVariable Long page,
            @PathVariable Long limit) {
        Page<SkuInfo> pageParam = new Page<>();
        pageParam.setCurrent(page);
        pageParam.setSize(limit);
        SkuInfoPageDTO skuServicePage = skuService.getPage(pageParam);
        return Result.ok(skuServicePage);
    }

    @GetMapping("admin/product/onSale/{skuId}")
    public Result onSale(@PathVariable("skuId") Long skuId) {
        skuService.onSale(skuId);
        // 添加至es中
        // searchApiClient.upperGoods(skuId);
        // 更改为使用 mq发送消息异步调用
        return Result.ok();
    }

    @GetMapping("admin/product/cancelSale/{skuId}")
    public Result cancelSale(@PathVariable("skuId") Long skuId) {
        skuService.offSale(skuId);
        // 从es中删除
        // searchApiClient.lowerGoods(skuId);
        return Result.ok();
    }


}
