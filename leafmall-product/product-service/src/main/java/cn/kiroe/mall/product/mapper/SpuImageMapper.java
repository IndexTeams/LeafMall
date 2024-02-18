package cn.kiroe.mall.product.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.kiroe.mall.product.model.SpuImage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SpuImageMapper extends BaseMapper<SpuImage> {
    default List<SpuImage> selectBySpuId(Long spuId){
        return selectList(new LambdaQueryWrapper<SpuImage>()
                .eq(SpuImage::getSpuId,spuId));
    }
}
