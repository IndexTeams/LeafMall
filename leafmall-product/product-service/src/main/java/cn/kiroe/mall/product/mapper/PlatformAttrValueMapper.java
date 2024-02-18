package cn.kiroe.mall.product.mapper;

import cn.kiroe.mall.product.model.PlatformAttributeValue;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlatformAttrValueMapper extends BaseMapper<PlatformAttributeValue> {
    default List<PlatformAttributeValue> selectByAttrId(Long attrId) {
        return selectList(new LambdaQueryWrapper<PlatformAttributeValue>().eq(PlatformAttributeValue::getAttrId, attrId));
    }

    Integer delByAttrIdNotLogic(Long attrId);
}
