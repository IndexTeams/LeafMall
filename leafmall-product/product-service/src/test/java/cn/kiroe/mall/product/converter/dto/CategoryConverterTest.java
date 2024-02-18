package cn.kiroe.mall.product.converter.dto;

import cn.kiroe.mall.product.dto.FirstLevelCategoryDTO;
import cn.kiroe.mall.product.model.FirstLevelCategory;
import com.github.xiaoymin.knife4j.annotations.Ignore;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author Kiro
 * @Date 2024/01/18 08:59
 **/
@SpringBootTest
@Slf4j
class CategoryConverterTest {
    @Autowired
    CategoryConverter categoryConverter;
    @Ignore
    void firstLevelCategoryPO2DTO() {
        FirstLevelCategoryDTO firstLevelCategoryDTO = categoryConverter.firstLevelCategoryPO2DTO(new FirstLevelCategory());
        log.info(firstLevelCategoryDTO.toString());

    }
}