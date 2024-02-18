package cn.kiroe.mall.product.mapper;


import cn.kiroe.mall.product.model.CategoryTrademark;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryTrademarkMapper extends BaseMapper<CategoryTrademark> {
    default List<CategoryTrademark> selectByThirdLevelCategoryId(Long category3Id) {
        return selectList(new LambdaQueryWrapper<CategoryTrademark>().
                eq(CategoryTrademark::getThirdLevelCategoryId, category3Id));
    }

    default int delete(Long thirdLevelCategoryId, final Long trademarkId) {
        return delete(new LambdaQueryWrapper<CategoryTrademark>()
                .eq(CategoryTrademark::getThirdLevelCategoryId, thirdLevelCategoryId)
                .eq(CategoryTrademark::getTrademarkId, trademarkId));
    }
}
