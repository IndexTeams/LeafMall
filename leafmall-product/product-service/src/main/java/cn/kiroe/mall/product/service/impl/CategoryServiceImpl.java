package cn.kiroe.mall.product.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.kiroe.mall.common.constant.RedisConst;
import cn.kiroe.mall.product.converter.dto.CategoryConverter;
import cn.kiroe.mall.product.converter.dto.CategoryTrademarkConverter;
import cn.kiroe.mall.product.dto.*;
import cn.kiroe.mall.product.mapper.*;
import cn.kiroe.mall.product.model.*;
import cn.kiroe.mall.product.query.CategoryTrademarkParam;
import cn.kiroe.mall.product.service.CategoryService;
import cn.kiroe.mall.redis.annotation.RedisCache;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import io.github.linpeilie.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Kiro
 * @Date 2024/01/18 10:14
 **/
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryConverter categoryConverter;
    private final Converter converter;
    private final FirstLevelCategoryMapper firstLevelCategoryMapper;
    private final SecondLevelCategoryMapper secondLevelCategoryMapper;
    private final ThirdLevelCategoryMapper thirdLevelCategoryMapper;
    private final CategoryTrademarkMapper categoryTrademarkMapper;
    private final TrademarkMapper trademarkMapper;
    private final CategoryTrademarkConverter categoryTrademarkConverter;


    /**
     * 查询一级分类
     *
     * @return
     */
    @Override
    public List<FirstLevelCategoryDTO> getFirstLevelCategory() {
        // 查询
        List<FirstLevelCategory> firstLevelCategories = firstLevelCategoryMapper.selectList(null);
        // 进行转换
        return categoryConverter.firstLevelCategoryPOs2DTOs(firstLevelCategories);

    }

    /**
     * 根据 一级查询二级
     *
     * @param firstLevelCategoryId
     * @return
     */
    @Override
    public List<SecondLevelCategoryDTO> getSecondLevelCategory(final Long firstLevelCategoryId) {
        return categoryConverter.secondLevelCategoryPOs2DTOs(
                secondLevelCategoryMapper.selectByFirstLevelCategoryId(firstLevelCategoryId)
        );
    }

    /**
     * 根据2级目录查3级目录
     *
     * @param secondLevelCategoryId
     * @return
     */
    @Override
    public List<ThirdLevelCategoryDTO> getThirdLevelCategory(final Long secondLevelCategoryId) {
        List<ThirdLevelCategory> thirdLevelCategories = thirdLevelCategoryMapper.selectBySecondLevelCategoryId(secondLevelCategoryId);
        return categoryConverter.thirdLevelCategoryPOs2DTOs(thirdLevelCategories);
    }

    /**
     * 根据三级分类获取品牌
     *
     * @param category3Id
     * @return
     */
    @Override
    public List<TrademarkDTO> findTrademarkList(final Long category3Id) {
        List<CategoryTrademark> categoryTrademarks = categoryTrademarkMapper.selectByThirdLevelCategoryId(category3Id);
        if (CollUtil.isEmpty(categoryTrademarks)) {
            return new ArrayList<>();
        }
        // 获取结果
        List<Trademark> trademarks = trademarkMapper
                .selectBatchIds(categoryTrademarks.stream().map(CategoryTrademark::getTrademarkId).collect(Collectors.toList()));
        return converter.convert(trademarks, TrademarkDTO.class);
    }

    /**
     * 保存品牌于分类关联
     *
     * @param categoryTrademarkParam
     */
    @Override
    public void save(final CategoryTrademarkParam categoryTrademarkParam) {
        // 判断品牌和分类是否存在
        if (thirdLevelCategoryMapper.selectById(categoryTrademarkParam.getCategory3Id()) == null
                && thirdLevelCategoryMapper.selectBatchIds(categoryTrademarkParam.getTrademarkIdList()).isEmpty()) {
            throw new RuntimeException("品牌或者分类不存在");
        }
        // 插入结果
        // 将param转为PO
        List<CategoryTrademark> categoryTrademarkList = categoryTrademarkConverter.param2PO(categoryTrademarkParam);
        boolean savedBatch = Db.saveBatch(categoryTrademarkList);
        if (!savedBatch) {
            throw new RuntimeException("插入失败");
        }
    }

    @Override
    public void remove(final Long thirdLevelCategoryId, final Long trademarkId) {
        int delete = categoryTrademarkMapper.delete(thirdLevelCategoryId, trademarkId);
        if (delete != 1) {
            new RuntimeException("删除失败");
        }

    }

    @Override
    public CategoryHierarchyDTO getCategoryViewByCategoryId(final Long thirdLevelCategoryId) {
        // 1. 更具三级分类查询三级分类信息
        ThirdLevelCategory thirdLevelCategory = thirdLevelCategoryMapper.selectById(thirdLevelCategoryId);
        // 2. 3 -> 2 -> 1
        SecondLevelCategory secondLevelCategory = secondLevelCategoryMapper.selectById(thirdLevelCategory.getSecondLevelCategoryId());
        FirstLevelCategory firstLevelCategory = firstLevelCategoryMapper.selectById(secondLevelCategory.getFirstLevelCategoryId());
        CategoryHierarchyDTO categoryHierarchyDTO = new CategoryHierarchyDTO();
        categoryHierarchyDTO.setFirstLevelCategoryId(firstLevelCategory.getId());
        categoryHierarchyDTO.setFirstLevelCategoryName(firstLevelCategory.getName());

        categoryHierarchyDTO.setSecondLevelCategoryId(secondLevelCategory.getId());
        categoryHierarchyDTO.setSecondLevelCategoryName(secondLevelCategory.getName());

        categoryHierarchyDTO.setThirdLevelCategoryId(thirdLevelCategoryId);
        categoryHierarchyDTO.setThirdLevelCategoryName(thirdLevelCategory.getName());
        return categoryHierarchyDTO;
    }

    /**
     * 获取分类目录 : 1->2->3
     * 所以
     *
     * @return
     */
    @Override
    @RedisCache(prefix = RedisConst.CATEGORY_PREFIX + "TreeList")
    public List<FirstLevelCategoryNodeDTO> getCategoryTreeList() {
        List<FirstLevelCategoryNodeDTO> firstLevelCategoryNodeDTOS = firstLevelCategoryMapper.selectAllNodeDTO();
        // 设置序号
        for (int i = 0; i < firstLevelCategoryNodeDTOS.size(); i++) {
            FirstLevelCategoryNodeDTO firstLevelCategoryNodeDTO = firstLevelCategoryNodeDTOS.get(i);
            firstLevelCategoryNodeDTO.setIndex(i);
        }
        return firstLevelCategoryNodeDTOS;
    }
}
