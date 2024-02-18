package cn.kiroe.mall.product.mapper;

import cn.kiroe.mall.product.model.SecondLevelCategory;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SecondLevelCategoryMapper extends BaseMapper<SecondLevelCategory> {

    default List<SecondLevelCategory> selectByFirstLevelCategoryId(Long firstLevelCategoryId){
        return selectList(new LambdaQueryWrapper<SecondLevelCategory>()
                .eq(SecondLevelCategory::getFirstLevelCategoryId, firstLevelCategoryId));
    }
}
