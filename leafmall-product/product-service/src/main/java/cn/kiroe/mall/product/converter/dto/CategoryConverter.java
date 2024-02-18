package cn.kiroe.mall.product.converter.dto;

import cn.kiroe.mall.product.dto.*;
import cn.kiroe.mall.product.model.CategoryHierarchy;
import cn.kiroe.mall.product.model.FirstLevelCategory;
import cn.kiroe.mall.product.model.SecondLevelCategory;
import cn.kiroe.mall.product.model.ThirdLevelCategory;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING )
public interface CategoryConverter {

    FirstLevelCategoryDTO firstLevelCategoryPO2DTO(FirstLevelCategory firstLevelCategory);
    List<FirstLevelCategoryDTO> firstLevelCategoryPOs2DTOs(List<FirstLevelCategory> firstLevelCategories);

    SecondLevelCategoryDTO secondLevelCategoryPO2DTO(SecondLevelCategory secondLevelCategory);
    List<SecondLevelCategoryDTO> secondLevelCategoryPOs2DTOs(List<SecondLevelCategory> secondLevelCategories);

    ThirdLevelCategoryDTO thirdLevelCategoryPO2DTO(ThirdLevelCategory thirdLevelCategory);
    List<ThirdLevelCategoryDTO> thirdLevelCategoryPOs2DTOs(List<ThirdLevelCategory> thirdLevelCategories);
    CategoryHierarchyDTO categoryViewPO2DTO(CategoryHierarchy categoryHierarchy);

    default FirstLevelCategoryNodeDTO firstPO2NodeDTO(FirstLevelCategory firstLevelCategory,Integer index,List<SecondLevelCategoryNodeDTO> categoryChild){
        FirstLevelCategoryNodeDTO firstLevelCategoryNodeDTO = new FirstLevelCategoryNodeDTO();
        firstLevelCategoryNodeDTO.setCategoryId(firstLevelCategory.getId());
        firstLevelCategoryNodeDTO.setCategoryName(firstLevelCategory.getName());
        firstLevelCategoryNodeDTO.setIndex(index);
        firstLevelCategoryNodeDTO.setCategoryChild(categoryChild);
        return firstLevelCategoryNodeDTO;
    }


}
