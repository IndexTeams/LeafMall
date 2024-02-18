package cn.kiroe.mall.product.mapper;

import cn.kiroe.mall.product.model.SkuImage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SkuImageMapper extends BaseMapper<SkuImage> {

    List<SkuImage> getSkuImages(@Param("skuId") Long skuId);
}
