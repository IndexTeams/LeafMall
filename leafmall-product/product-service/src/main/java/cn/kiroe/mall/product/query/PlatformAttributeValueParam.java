package cn.kiroe.mall.product.query;

import cn.kiroe.mall.product.model.PlatformAttributeValue;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

@Data
@AutoMapper(target = PlatformAttributeValue.class)
public class PlatformAttributeValueParam {

    //"平台属性值id"
    private Long id;

    //"属性值名称"
    private String valueName;

    //"属性id"
    private Long attrId;
}
