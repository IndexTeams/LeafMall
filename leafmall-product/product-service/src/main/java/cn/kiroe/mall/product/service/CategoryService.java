package cn.kiroe.mall.product.service;

import cn.kiroe.mall.product.dto.*;
import cn.kiroe.mall.product.query.CategoryTrademarkParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface CategoryService {

    /**
     * 查询所有的一级分类信息
     * @return
     */
    List<FirstLevelCategoryDTO> getFirstLevelCategory();

    /**
     * 根据一级分类Id 查询二级分类数据
     */
    List<SecondLevelCategoryDTO> getSecondLevelCategory(Long firstLevelCategoryId);

    /**
     * 根据二级分类Id 查询三级分类数据
     */
    List<ThirdLevelCategoryDTO> getThirdLevelCategory(Long secondLevelCategoryId);

    /**
     * 根据三级分类获取品牌
     */
    List<TrademarkDTO> findTrademarkList(Long category3Id);

    /**
     * 保存分类与品牌关联
     */
    void save(CategoryTrademarkParam categoryTrademarkParam);

    /**
     * 删除关联
     */
    void remove(Long thirdLevelCategoryId, Long trademarkId);

    /*
        获取和该三级类目相关完整信息(包括它所属的一级类目和二级类目）
     */
    CategoryHierarchyDTO getCategoryViewByCategoryId(Long thirdLevelCategoryId);

    /*
          获取完整的三级类目信息
     */
    List<FirstLevelCategoryNodeDTO> getCategoryTreeList();


}
