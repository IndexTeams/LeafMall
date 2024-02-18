package cn.kiroe.mall.product.query;

import com.baomidou.mybatisplus.annotation.TableField;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PlatformAttributeParam {

    // "平台属性id"
    private Long id;

    //"属性名称"
    @NotEmpty(message = "属性名称为空")
    private String attrName;

    //"分类id"
    @NotNull(message = "不能为空")
    @TableField("category_id")
    private Long categoryId;

    //"分类层级"
    @NotNull(message = "不能为空")
    private Integer categoryLevel;

    /*
        平台属性值集合，这里注意一个平台属性，有多个属性取值
     */
    //"平台属性值列表"， 必须要有值
    @NotEmpty(message = "必须要有属性值列表")
    private List<PlatformAttributeValueParam> attrValueList;
}
