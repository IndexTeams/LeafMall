package cn.kiroe.mall.product.query;

import cn.kiroe.mall.product.model.SpuPoster;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

@Data
@AutoMapper(target = SpuPoster.class)
public class SpuPosterParam {

    private Long id;

    // "商品id"
    private Long spuId;

    // "文件名称"
    private String imgName;

    // "文件路径"
    private String imgUrl;
}
