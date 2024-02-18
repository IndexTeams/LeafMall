package cn.kiroe.mall.product.controller;

import cn.kiroe.mall.common.result.Result;
import cn.kiroe.mall.product.dto.SaleAttributeInfoDTO;
import cn.kiroe.mall.product.dto.SpuImageDTO;
import cn.kiroe.mall.product.dto.SpuInfoPageDTO;
import cn.kiroe.mall.product.dto.SpuSaleAttributeInfoDTO;
import cn.kiroe.mall.product.query.SpuInfoParam;
import cn.kiroe.mall.product.service.SpuService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Kiro
 * @Date 2024/01/18 22:51
 **/
@RestController
@RequiredArgsConstructor
public class SpuController {
    private final SpuService spuService;

    /**
     * 查询所有销售属性
     * @return
     */
    @GetMapping("admin/product/baseSaleAttrList")
    public Result SaleAttrList(){
        List<SaleAttributeInfoDTO> saleAttrInfoList = spuService.getSaleAttrInfoList();
        return Result.ok(saleAttrInfoList);
    }
    @GetMapping("admin/product/{page}/{size}")
    public Result getSpuInfoPage(@PathVariable Long page,
                                 @PathVariable Long size,
                                 Long category3Id) {

        Page pageParam = new Page();
        pageParam.setCurrent(page);
        pageParam.setSize(size);
        SpuInfoPageDTO spuInfoPage = spuService.getSpuInfoPage(pageParam, category3Id);
        return Result.ok(spuInfoPage);
    }

    @PostMapping("admin/product/saveSpuInfo")
    public Result saveSpuInfo(@RequestBody SpuInfoParam spuInfoParam){
        spuService.saveSpuInfo(spuInfoParam);
        return Result.ok();
    }

    @GetMapping("admin/product/spuImageList/{spuId}")
    public Result<List<SpuImageDTO>> getSpuImageList(@PathVariable("spuId") Long spuId) {
        List<SpuImageDTO> spuImageList = spuService.getSpuImageList(spuId);
        return Result.ok(spuImageList);
    }

    @GetMapping("admin/product/spuSaleAttrList/{spuId}")
    public Result<List<SpuSaleAttributeInfoDTO>> getSpuSaleAttrList(@PathVariable("spuId") Long spuId) {
        List<SpuSaleAttributeInfoDTO> spuSaleAttrList = spuService.getSpuSaleAttrList(spuId);
        return Result.ok(spuSaleAttrList);
    }




}
