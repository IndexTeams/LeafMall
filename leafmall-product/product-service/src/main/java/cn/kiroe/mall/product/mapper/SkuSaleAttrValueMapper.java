package cn.kiroe.mall.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.kiroe.mall.product.model.SkuSaleAttributeValue;
import cn.kiroe.mall.product.model.SkuSaleAttributeValuePermutation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SkuSaleAttrValueMapper extends BaseMapper<SkuSaleAttributeValue> {

    // 根据spuId 查询map 集合数据
    List<SkuSaleAttributeValuePermutation> selectSaleAttrValuesBySpu(Long spuId);

}
