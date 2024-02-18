package cn.kiroe.mall.product.controller;

import cn.kiroe.mall.common.result.Result;
import cn.kiroe.mall.product.dto.TrademarkDTO;
import cn.kiroe.mall.product.dto.TrademarkPageDTO;
import cn.kiroe.mall.product.model.Trademark;
import cn.kiroe.mall.product.query.TrademarkParam;
import cn.kiroe.mall.product.service.TrademarkService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Kiro
 * @Date 2024/01/18 20:16
 **/
@RestController
@RequiredArgsConstructor
public class TrademarkController {
    private final TrademarkService trademarkService;
    // http://localhost/admin/product/baseTrademark/1/10
    // 查看品牌列表
    @GetMapping("/admin/product/baseTrademark/{pageNo}/{pageSize}")
    public Result<TrademarkPageDTO> getTradeMarkDTOList(@PathVariable Long pageNo, @PathVariable Long pageSize) {
        Page<Trademark> pageParam = new Page<>();
        pageParam.setCurrent(pageNo);
        pageParam.setSize(pageSize);
        TrademarkPageDTO page = trademarkService.getPage(pageParam);
        return Result.ok(page);
    }


    // 保存品牌
// http://localhost/admin/product/baseTrademark/save
    @PostMapping("/admin/product/baseTrademark/save")
    public Result save(@RequestBody TrademarkParam trademarkParam) {
        trademarkService.save(trademarkParam);
        return Result.ok();
    }

    // http://localhost/admin/product/baseTrademark/remove/10
// 删除品牌
    @DeleteMapping("/admin/product/baseTrademark/remove/{tradeMarkId}")
    public Result deleteById(@PathVariable Long tradeMarkId) {
        trademarkService.removeById(tradeMarkId);
        return Result.ok();

    }


    // http://localhost/admin/product/baseTrademark/get/17
// 查询品牌
    @GetMapping("/admin/product/baseTrademark/get/{tradeMarkId}")
    public Result<TrademarkDTO> getTradeMarkDTO(@PathVariable Long tradeMarkId) {
        TrademarkDTO trademark = trademarkService.getTrademarkByTmId(tradeMarkId);
        return Result.ok(trademark);

    }

    // 修改品牌
// http://localhost/admin/product/baseTrademark/update
    @PutMapping("/admin/product/baseTrademark/update")
    public Result updateTradeMark(@RequestBody TrademarkParam trademarkParam) {
        trademarkService.updateById(trademarkParam);
        return Result.ok();

    }
    // 找到当前 未关联到手机的品牌
    @GetMapping("admin/product/baseCategoryTrademark/findCurrentTrademarkList/{thirdLevelCategoryId}")
    public Result findCurrentTrademarkList(@PathVariable Long thirdLevelCategoryId){
        return Result.ok(trademarkService.findUnLinkedTrademarkList(thirdLevelCategoryId));
    }
    
}
