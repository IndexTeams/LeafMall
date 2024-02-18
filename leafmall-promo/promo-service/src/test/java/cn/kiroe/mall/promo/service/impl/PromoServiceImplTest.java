package cn.kiroe.mall.promo.service.impl;

import cn.kiroe.mall.promo.ServicePromoApplication;
import cn.kiroe.mall.promo.service.PromoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author Kiro
 * @Date 2024/02/05 17:01
 **/
@SpringBootTest(classes = ServicePromoApplication.class)
class PromoServiceImplTest {
    @Autowired
    PromoService promoService;

    @Test
    void importIntoRedis() {
        promoService.importIntoRedis();

    }
}