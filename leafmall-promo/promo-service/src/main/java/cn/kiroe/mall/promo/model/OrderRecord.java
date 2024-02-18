package cn.kiroe.mall.promo.model;

import cn.kiroe.mall.promo.api.dto.SeckillGoodsDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户秒杀成功，订单记录
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRecord implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userId;

	private SeckillGoods seckillGoods;

	private Integer num;

	private String orderStr;
}
