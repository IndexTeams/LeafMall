package cn.kiroe.mall.order.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kiroe.mall.order.dto.OrderInfoDTO;
import cn.kiroe.mall.order.model.OrderInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

    /**
     * 通过
     * @param page
     * @param userId
     * @return
     */
    IPage<OrderInfoDTO> selectOrderInfoPageExcludeOrderStatsByUserId(@Param("page") Page<OrderInfoDTO> page,@Param("userId") Long userId);

    OrderInfoDTO selectOneById(@Param("id")Long id);
}
