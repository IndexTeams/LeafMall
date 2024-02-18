package cn.kiroe.mall.product.controller.inner;

import cn.kiroe.mall.product.dto.CategoryHierarchyDTO;
import cn.kiroe.mall.product.dto.PlatformAttributeInfoDTO;
import cn.kiroe.mall.product.dto.SkuInfoDTO;
import cn.kiroe.mall.product.dto.TrademarkDTO;
import cn.kiroe.mall.product.service.CategoryService;
import cn.kiroe.mall.product.service.SkuService;
import cn.kiroe.mall.product.service.TrademarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author Kiro
 * @Date 2024/01/27 16:05
 **/
@RestController
@RequiredArgsConstructor
public class ProductInnerController {
    private final SkuService skuService;
    private final CategoryService categoryService;
    private final TrademarkService trademarkService;

    @GetMapping("/api/product/inner/getSkuInfo/{skuId}")
    SkuInfoDTO getSkuInfo(@PathVariable("skuId") Long skuId) {
        return skuService.getSkuInfo(skuId);
    }


    /**
     * 通过三级分类id查询分类信息
     *
     * @param category3Id
     * @return
     */
    @GetMapping("/api/product/inner/getCategoryView/{thirdLevelCategoryId}")
    CategoryHierarchyDTO getCategoryView(@PathVariable("thirdLevelCategoryId") Long thirdLevelCategoryId) {
        return categoryService.getCategoryViewByCategoryId(thirdLevelCategoryId);
    }


    /**
     * 通过skuId 集合来查询数据
     *
     * @param skuId
     */
    @GetMapping("/api/product/inner/getAttrList/{skuId}")
    List<PlatformAttributeInfoDTO> getAttrList(@PathVariable("skuId") Long skuId) {
        return skuService.getPlatformAttrInfoBySku(skuId);
    }


    /**
     * 通过品牌Id 集合来查询数据
     *
     * @param tmId
     * @return
     */
    @GetMapping("/api/product/inner/getTrademark/{tmId}")
    TrademarkDTO getTrademark(@PathVariable("tmId") Long tmId) {
        return trademarkService.getTrademarkByTmId(tmId);
    }

    // 获取商品的最新价格
    @GetMapping("/api/product/inner/getSkuPrice/{skuId}")
    public BigDecimal getSkuPrice(@PathVariable(value = "skuId") Long skuId) {
        return skuService.getSkuPrice(skuId);
    }
}
