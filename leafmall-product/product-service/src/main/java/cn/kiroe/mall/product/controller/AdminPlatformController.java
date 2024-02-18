package cn.kiroe.mall.product.controller;

import cn.kiroe.mall.common.result.Result;
import cn.kiroe.mall.product.dto.PlatformAttributeInfoDTO;
import cn.kiroe.mall.product.dto.PlatformAttributeValueDTO;
import cn.kiroe.mall.product.query.PlatformAttributeParam;
import cn.kiroe.mall.product.service.PlatformAttributeService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author Kiro
 * @Date 2024/01/18 11:09
 **/
@RestController
@RequiredArgsConstructor
public class AdminPlatformController {
    private final PlatformAttributeService platformAttributeService;
    // 根据分类Id查询平台属性以及平台属性值
    // http://localhost/admin/product/attrInfoList/3/20/149
    @GetMapping("/admin/product/attrInfoList/{firstLevelCategoryId}/{secondLevelCategoryId}/{thirdLevelCategoryId}")
    public Result getAttrInfoList(@PathVariable Long firstLevelCategoryId,
                                  @PathVariable Long secondLevelCategoryId,
                                  @PathVariable Long thirdLevelCategoryId){
        List<PlatformAttributeInfoDTO> platformAttrInfoList = platformAttributeService.getPlatformAttrInfoList(firstLevelCategoryId, secondLevelCategoryId, thirdLevelCategoryId);
        return Result.ok(platformAttrInfoList);
    }

    // 保存平台属性 ，更新与添加 调用的都为同一个接口
    //  http://localhost/admin/product/saveAttrInfo
    @PostMapping("/admin/product/saveAttrInfo")
    public Result saveAttrInfo(@RequestBody PlatformAttributeParam platformAttributeParam) {
        platformAttributeService.savePlatformAttrInfo(platformAttributeParam);
        return Result.ok();
    }

    // http://localhost/admin/product/getAttrValueList/106
// 平台属性值回显
    @GetMapping("/admin/product/getAttrValueList/{attrId}")
    public Result<List<PlatformAttributeValueDTO>> getAttrValueDTO(@NotNull @PathVariable Long attrId) {
        List<PlatformAttributeValueDTO> platformAttrValueList = platformAttributeService.getPlatformAttrValueList(attrId);
        return Result.ok(platformAttrValueList);
    }
}
