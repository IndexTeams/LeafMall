package cn.kiroe.mall.product.controller;

import cn.kiroe.mall.common.result.Result;
import cn.kiroe.mall.product.dto.*;
import cn.kiroe.mall.product.query.CategoryTrademarkParam;
import cn.kiroe.mall.product.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Kiro
 * @Date 2024/01/18 10:12
 **/
@RestController
@RequiredArgsConstructor
public class AdminCategoryController {


    private final CategoryService categoryService;

    /**
     * 查询所有一级分类
     */
    @GetMapping("admin/product/getCategory1")
    public Result<List<FirstLevelCategoryDTO>> getCategory1(){
        List<FirstLevelCategoryDTO> firstLevelCategory = categoryService.getFirstLevelCategory();
        return  Result.ok(firstLevelCategory);
    }
    /**
     * 根据一级id
     * 查询二级分类
     */
    // 根据一级分类查询二级分类
    @GetMapping("/admin/product/getCategory2/{firstLevelCategoryId}")
    public Result<List<SecondLevelCategoryDTO>> getCategory2(@PathVariable Long firstLevelCategoryId){
        List<SecondLevelCategoryDTO> secondLevelCategory = categoryService.getSecondLevelCategory(firstLevelCategoryId);
        return Result.ok(secondLevelCategory);
    }

    // 根据二级分类，查询三级分类
    @GetMapping("/admin/product/getCategory3/{category2Id}")
    public Result<List<ThirdLevelCategoryDTO>> getCategory3(@PathVariable Long category2Id){
        List<ThirdLevelCategoryDTO> thirdLevelCategory = categoryService.getThirdLevelCategory(category2Id);
        return Result.ok(thirdLevelCategory);
    }

    @PostMapping("admin/product/baseCategoryTrademark/save")
    public Result save(@RequestBody CategoryTrademarkParam categoryTrademarkVo){
        categoryService.save(categoryTrademarkVo);
        return Result.ok();
    }

    @GetMapping("admin/product/baseCategoryTrademark/findTrademarkList/{thirdLevelCategoryId}")
    public Result findTrademarkList(@PathVariable Long thirdLevelCategoryId){
        List<TrademarkDTO> trademarkList = categoryService.findTrademarkList(thirdLevelCategoryId);
        return Result.ok(trademarkList);
    }
    @DeleteMapping("admin/product/baseCategoryTrademark/remove/{thirdLevelCategoryId}/{trademarkId}")
    public Result remove(@PathVariable Long thirdLevelCategoryId, @PathVariable Long trademarkId){
        // 删除关联
        categoryService.remove(thirdLevelCategoryId,trademarkId);
        return Result.ok();
    }

    /**
     * 获取全部分类信息
     * @return
     */
    @GetMapping("/index")
    public Result getBaseCategoryList(){
        List<FirstLevelCategoryNodeDTO> categoryTreeList = categoryService.getCategoryTreeList();
        return Result.ok(categoryTreeList);
    }


}
