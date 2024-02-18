package cn.kiroe.mall.order.dto;

import cn.kiroe.mall.user.dto.UserAddressDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 创建日期: 2023/03/15 15:09
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderTradeDTO {

    // TODO 修改 userAddressList
    // 当前用户的收获地址列表
    List<UserAddressDTO> userAddressList;
    // 订单详情
    List<OrderDetailDTO> detailArrayList;
    // 总数量
    Integer totalNum;
    // 总价格
    BigDecimal totalAmount;
}
