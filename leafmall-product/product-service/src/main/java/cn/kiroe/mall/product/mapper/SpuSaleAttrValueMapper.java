package cn.kiroe.mall.product.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.kiroe.mall.product.model.SpuSaleAttributeValue;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SpuSaleAttrValueMapper extends BaseMapper<SpuSaleAttributeValue> {

    default List<SpuSaleAttributeValue> selectBySpuId(Long spuId){
        return selectList(new LambdaQueryWrapper<SpuSaleAttributeValue>()
                .eq(SpuSaleAttributeValue::getSpuId,spuId));
    }
}
