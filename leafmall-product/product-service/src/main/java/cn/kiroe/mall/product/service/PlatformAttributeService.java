package cn.kiroe.mall.product.service;

import cn.kiroe.mall.product.dto.PlatformAttributeInfoDTO;
import cn.kiroe.mall.product.dto.PlatformAttributeValueDTO;
import cn.kiroe.mall.product.model.PlatformAttributeInfo;
import cn.kiroe.mall.product.model.PlatformAttributeValue;
import cn.kiroe.mall.product.query.PlatformAttributeParam;
import cn.kiroe.mall.product.query.PlatformAttributeValueParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface PlatformAttributeService {

    /**
     * 根据分类Id 获取平台属性数据
     * 接口说明：
     * 1，平台属性可以挂在一级分类、二级分类和三级分类
     * 2，查询一级分类下面的平台属性，传：firstlevelCatogoryId，0，0；   取出该分类的平台属性
     * 3，查询二级分类下面的平台属性，传：firstlevelCatogoryId，category2Id，0；
     *    取出对应一级分类下面的平台属性与二级分类对应的平台属性
     * 4，查询三级分类下面的平台属性，传：firstlevelCatogoryId，category2Id，category3Id；
     *    取出对应一级分类、二级分类与三级分类对应的平台属性
     */
    List<PlatformAttributeInfoDTO> getPlatformAttrInfoList(Long firstLevelCategoryId, Long secondLevelCategoryId,
     Long thirdLevelCategoryId);

    /*
         保存平台属性及平台属性值
     */
    void savePlatformAttrInfo(PlatformAttributeParam platformAttributeParam);

    /*
            获取平台属性值列表
     */
    List<PlatformAttributeValueDTO> getPlatformAttrValueList(Long attrId);



}