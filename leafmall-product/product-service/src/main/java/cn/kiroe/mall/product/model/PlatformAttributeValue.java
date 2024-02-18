package cn.kiroe.mall.product.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.kiroe.mall.common.model.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * <p>
 * PlatformAttributeValue
 * </p>
 *
 */
@Data
@TableName("platform_attr_value")
public class PlatformAttributeValue extends BaseEntity {

	private static final long serialVersionUID = 1L;

	//"属性值名称"
	@TableField("value_name")
	private String valueName;

	//"属性id"
	@TableField("attr_id")
	private Long attrId;
}

