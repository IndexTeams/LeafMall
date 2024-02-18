package cn.kiroe.mall.payment.service.impl;

import cn.kiroe.mall.pay.api.dto.AlipayTradeQueryResponseDTO;
import cn.kiroe.mall.payment.service.AliPayService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author Kiro
 * @Date 2024/02/03 15:53
 **/
@SpringBootTest
class AliPayServiceImplTest {
    @Autowired
    AliPayService alipayservice;
    void getAlipayInfo() {
        AlipayTradeQueryResponseDTO alipayInfo = alipayservice.getAlipayInfo("61fe893676484293817ed05228c34ae7");
        System.out.println("alipayInfo = " + alipayInfo);
    }
}