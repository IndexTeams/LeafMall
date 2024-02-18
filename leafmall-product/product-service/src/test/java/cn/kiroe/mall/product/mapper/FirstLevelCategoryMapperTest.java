package cn.kiroe.mall.product.mapper;

import cn.kiroe.mall.product.dto.FirstLevelCategoryNodeDTO;
import com.github.xiaoymin.knife4j.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author Kiro
 * @Date 2024/01/22 21:59
 **/
@SpringBootTest
class FirstLevelCategoryMapperTest {
    @Autowired
    FirstLevelCategoryMapper firstLevelCategoryMapper;
    @Ignore
    void selectAllNodeDTO() {
        List<FirstLevelCategoryNodeDTO> firstLevelCategoryNodeDTOS = firstLevelCategoryMapper.selectAllNodeDTO();

    }
}