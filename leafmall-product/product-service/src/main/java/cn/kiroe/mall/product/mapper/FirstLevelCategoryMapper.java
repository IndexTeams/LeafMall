package cn.kiroe.mall.product.mapper;


import cn.kiroe.mall.product.dto.FirstLevelCategoryDTO;
import cn.kiroe.mall.product.dto.FirstLevelCategoryNodeDTO;
import cn.kiroe.mall.product.model.FirstLevelCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FirstLevelCategoryMapper extends BaseMapper<FirstLevelCategory> {
    List<FirstLevelCategoryNodeDTO> selectAllNodeDTO();
}
