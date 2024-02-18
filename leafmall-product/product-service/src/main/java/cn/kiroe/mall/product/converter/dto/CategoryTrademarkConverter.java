package cn.kiroe.mall.product.converter.dto;

import cn.kiroe.mall.product.model.CategoryTrademark;
import cn.kiroe.mall.product.query.CategoryTrademarkParam;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Kiro
 * @Date 2024/01/18 22:16
 **/
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryTrademarkConverter {
    default List<CategoryTrademark> param2PO(CategoryTrademarkParam categoryTrademarkParam){
        // 插入结果
        List<CategoryTrademark> categoryTrademarkList = categoryTrademarkParam
                .getTrademarkIdList().stream().map(tId -> {
            CategoryTrademark categoryTrademark = new CategoryTrademark();
            categoryTrademark.setTrademarkId(tId);
            categoryTrademark.setThirdLevelCategoryId(categoryTrademarkParam.getCategory3Id());
            return categoryTrademark;
        }).collect(Collectors.toList());
        return categoryTrademarkList;
    }
}
