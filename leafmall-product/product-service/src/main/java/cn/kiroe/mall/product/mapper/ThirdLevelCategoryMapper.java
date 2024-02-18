package cn.kiroe.mall.product.mapper;

import cn.kiroe.mall.product.model.ThirdLevelCategory;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ThirdLevelCategoryMapper extends BaseMapper<ThirdLevelCategory> {

    default List<ThirdLevelCategory> selectBySecondLevelCategoryId(long secondLevelCategoryId) {
        return selectList(new LambdaQueryWrapper<ThirdLevelCategory>()
                .eq(ThirdLevelCategory::getSecondLevelCategoryId,
                        secondLevelCategoryId));
    }
}
