package cn.kiroe.mall.order.mapper;

import cn.kiroe.mall.order.dto.OrderInfoDTO;
import cn.kiroe.mall.order.model.OrderInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author Kiro
 * @Date 2024/02/01 17:03
 **/
@SpringBootTest
class OrderInfoMapperTest {
    @Autowired
    OrderInfoMapper orderInfoMapper;
    void selectOrderInfoPageExcludeOrderStatsByUserId() {
        Page<OrderInfoDTO> page = new Page<>();
        page.setCurrent(1);
        page.setSize(1);
        orderInfoMapper.selectOrderInfoPageExcludeOrderStatsByUserId(page, 1L);
        List<OrderInfoDTO> records = page.getRecords();
    }

    @Test
    void selectOneById() {
        OrderInfo orderInfo = orderInfoMapper.selectById(1130);
        System.out.println("orderInfo = " + orderInfo);
    }
}