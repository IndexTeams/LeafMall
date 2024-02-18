package cn.kiroe.mall.product.service.impl;

import cn.kiroe.mall.product.dto.ProductDetailDTO;
import cn.kiroe.mall.product.service.ProductDetailService;
import com.github.xiaoymin.knife4j.annotations.Ignore;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author Kiro
 * @Date 2024/01/22 10:42
 **/
@SpringBootTest
@Slf4j
class ProductDetailServiceImplTest {
    @Autowired
    ProductDetailService productDetailService;
    @Ignore
    void getItemBySkuId() {
        ProductDetailDTO itemBySkuId = productDetailService.getItemBySkuId(3L);
        System.out.println("itemBySkuId = " + itemBySkuId);
    }
}