package cn.kiroe.mall.product.model;

import cn.kiroe.mall.product.dto.TrademarkDTO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import cn.kiroe.mall.common.model.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

/**
 * <p>
 * Trademark
 * </p>
 *
 */
@Data
@TableName("trademark")
@AutoMapper(target = TrademarkDTO.class)
public class Trademark extends BaseEntity {

	private static final long serialVersionUID = 1L;

	//"属性值"
	@TableField("tm_name")
	private String tmName;

	//"品牌logo的图片路径"
	@TableField("logo_url")
	private String logoUrl;

}

