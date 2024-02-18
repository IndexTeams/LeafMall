package cn.kiroe.mall.product.mapper;

import cn.kiroe.mall.product.model.SpuSaleAttributeInfo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SpuSaleAttrInfoMapper extends BaseMapper<SpuSaleAttributeInfo> {
    // 查值
    default List<SpuSaleAttributeInfo> selectBySpuId(Long spuId) {
        return selectList(new LambdaQueryWrapper<SpuSaleAttributeInfo>().eq(SpuSaleAttributeInfo::getSpuId, spuId));
    }
    // 根据spuId 查询销售属性集合
    List<SpuSaleAttributeInfo> selectAllBySpuId(@Param("spuId") Long spuId);

    List<SpuSaleAttributeInfo> selectSpuSaleAttrListCheckedBySku(@Param("skuId") Long skuId, @Param("spuId") Long spuId);
}
