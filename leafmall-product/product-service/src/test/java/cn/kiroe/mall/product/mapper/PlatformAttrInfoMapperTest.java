package cn.kiroe.mall.product.mapper;

import cn.kiroe.mall.product.model.PlatformAttributeInfo;
import cn.kiroe.mall.product.model.PlatformAttributeValue;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.github.xiaoymin.knife4j.annotations.Ignore;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author Kiro
 * @Date 2024/01/18 11:46
 **/
@SpringBootTest
@Slf4j
class PlatformAttrInfoMapperTest {
    @Autowired
    PlatformAttrInfoMapper platformAttrInfoMapper;
    @Ignore
    void selectPlatFormAttrInfoList() {
        List<PlatformAttributeInfo> platformAttributeInfos = platformAttrInfoMapper.selectPlatFormAttrInfoList(17L, 105L, 997L);
        log.info(platformAttributeInfos.toString());
    }

    @Ignore
    void testDb(){
        ArrayList<PlatformAttributeValue> platformAttributeValues = new ArrayList<>();
        PlatformAttributeValue e = new PlatformAttributeValue();
        e.setAttrId(120L);
        e.setValueName("test");
        platformAttributeValues.add(e);
        boolean b = Db.saveBatch(platformAttributeValues);
        log.info(String.valueOf(b));
    }
}