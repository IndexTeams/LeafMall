package cn.kiroe.mall.product.service.impl;

import cn.kiroe.mall.product.dto.PlatformAttributeInfoDTO;
import cn.kiroe.mall.product.service.SkuService;
import com.github.xiaoymin.knife4j.annotations.Ignore;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author Kiro
 * @Date 2024/01/22 16:40
 **/
@SpringBootTest
@Slf4j
class SkuServiceImplTest {
    @Autowired
    SkuService skuService;
    @Ignore
    void getPlatformAttrInfoBySku() {
        List<PlatformAttributeInfoDTO> platformAttrInfoBySku = skuService.getPlatformAttrInfoBySku(123L);
        log.info(platformAttrInfoBySku.toString());
    }
}