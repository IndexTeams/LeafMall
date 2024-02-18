package cn.kiroe.mall.product.mapper;

import cn.kiroe.mall.product.model.PlatformAttributeInfo;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PlatformAttrInfoMapper extends BaseMapper<PlatformAttributeInfo> {

    /**
     * 根据分类Id 查询平台属性集合对象
     */
    List<PlatformAttributeInfo> selectPlatFormAttrInfoList(@Param("firstLevelCategoryId") Long firstLevelCategoryId
            , @Param("secondLevelCategoryId") Long secondLevelCategoryId, @Param("thirdLevelCategoryId") Long thirdLevelCategoryId);

    // List<PlatformAttributeInfo> selectPlatformAttrInfoListBySkuId(Long skuId);

    default int updateAttrNameById(Long id, String attrName) {

        return update(new LambdaUpdateWrapper<PlatformAttributeInfo>()
                .set(PlatformAttributeInfo::getAttrName, attrName)
                .eq(PlatformAttributeInfo::getId, id)
        );
    }

}
