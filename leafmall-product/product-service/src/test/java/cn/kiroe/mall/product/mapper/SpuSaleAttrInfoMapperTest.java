package cn.kiroe.mall.product.mapper;

import cn.kiroe.mall.product.model.SpuSaleAttributeInfo;
import cn.kiroe.mall.product.model.SpuSaleAttributeValue;
import com.alibaba.fastjson.JSON;
import com.github.xiaoymin.knife4j.annotations.Ignore;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author Kiro
 * @Date 2024/01/19 16:40
 **/
@SpringBootTest
@Slf4j
class SpuSaleAttrInfoMapperTest {
    @Autowired
    SpuSaleAttrInfoMapper infoMapper;
    @Ignore
    void selectAllBySpuId() {
        List<SpuSaleAttributeInfo> spuSaleAttributeInfos = infoMapper.selectAllBySpuId(23L);
        log.info(spuSaleAttributeInfos.toString());
    }

    @Ignore
    void selectSpuSaleAttrListCheckedBySku() {
        List<SpuSaleAttributeInfo> spuSaleAttributeInfos = infoMapper.selectSpuSaleAttrListCheckedBySku(3L, 24L);
        log.info(spuSaleAttributeInfos.toString());
    }

    @Ignore
    void testMapperJson(){
        HashMap<List<Number>, String> listStringHashMap = new HashMap<>();
        listStringHashMap.put(Arrays.asList(1,2,3,4),"one");
        listStringHashMap.put(Arrays.asList(5,6,7),"two");
        listStringHashMap.put(Arrays.asList(8,9),"three");
        String jsonString = JSON.toJSONString(listStringHashMap);
        log.info(jsonString);
    }
}