package cn.kiroe.mall.product.service.impl;

import cn.kiroe.mall.product.dto.SpuSaleAttributeInfoDTO;
import cn.kiroe.mall.product.service.SpuService;
import com.github.xiaoymin.knife4j.annotations.Ignore;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

/**
 * @Author Kiro
 * @Date 2024/01/19 16:56
 **/
@SpringBootTest
@Slf4j
class SpuServiceImplTest {
    @Autowired
    SpuService spuService;

    @Ignore
    void getSpuSaleAttrList() {
        List<SpuSaleAttributeInfoDTO> spuSaleAttrList = spuService.getSpuSaleAttrList(23L);
        // log.info(spuSaleAttrList.toString());
    }

    void getSkuValueIdsMap() {
        Map<String, Long> skuValueIdsMap = spuService.getSkuValueIdsMap(24L);
    }
}