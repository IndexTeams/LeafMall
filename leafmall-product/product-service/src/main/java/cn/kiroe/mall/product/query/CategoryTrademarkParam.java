//
//
package cn.kiroe.mall.product.query;

import cn.kiroe.mall.product.model.CategoryTrademark;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * <p>
 * CategoryHierarchy
 * </p>
 *
 */
@Data
public class CategoryTrademarkParam {
	//"三级分类编号"
	@NotNull
	private Long category3Id;


	//"品牌id"
	@NotEmpty
	private List<Long> trademarkIdList;

}

