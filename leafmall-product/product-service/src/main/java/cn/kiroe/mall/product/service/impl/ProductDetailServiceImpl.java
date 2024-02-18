package cn.kiroe.mall.product.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.kiroe.mall.common.constant.RedisConst;
import cn.kiroe.mall.product.dto.*;
import cn.kiroe.mall.product.service.CategoryService;
import cn.kiroe.mall.product.service.ProductDetailService;
import cn.kiroe.mall.product.service.SkuService;
import cn.kiroe.mall.product.service.SpuService;
import cn.kiroe.mall.redis.annotation.RedisCache;
import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商品详情页
 *
 * @Author Kiro
 * @Date 2024/01/22 09:40
 **/
@Service
@RequiredArgsConstructor
public class ProductDetailServiceImpl implements ProductDetailService {
    private final SkuService skuService;
    private final SpuService spuService;
    private final CategoryService categoryService;
    private final RedissonClient redissonClient;

    /**
     * 获取商品详情页所需要的内容
     *
     * @param skuId
     * @return 需要获取哪些数据呢
     * 1. sku的基本信息:  重量，描述，描述，价格详情
     * 2. sku对应的图片信息
     * 3. sku对应的海报信息
     * 4. sku所属的 分类信息,一级，二级，三级
     * 5. sku所属的spu的所有的销售属性以及 销售属性值(当前sku选中的销售属性值)
     * 6. 为了sku的切换，需要查询 销售属性值的组合 和 skuId的映射关系
     * 7. 当前的sku所对应的平台属性以及各个平台属性对应的值
     */
    @Override
    public ProductDetailDTO getItemBySkuId(final Long skuId) {
        // 0. 判断该skuId是否在布隆过滤器中
        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(RedisConst.SKU_BLOOM_FILTER);
        boolean isContains = bloomFilter.contains(skuId);
        if(!isContains){
            // 说明布隆过滤器中不存在,直接拦截
            return new ProductDetailDTO();
        }


        // 1. 创建ProductDetailDto 对象
        ProductDetailDTO productDetailDTO = new ProductDetailDTO();
        // 2. 查询sku 基本信息（包含图片信息）
        SkuInfoDTO skuInfoDTO = skuService.getSkuInfo(skuId);
        if (skuInfoDTO == null || skuInfoDTO.getIsSale() == 0) {
            return null;
        }
        productDetailDTO.setSkuInfo(skuInfoDTO);
        // 3. 查询sku的价格
        productDetailDTO.setPrice(skuService.getSkuPrice(skuId));
        // 4. 查询sku所属的spu的所有的销售属性以及各个对应的值，以及挡圈sku相中的销售属性
        List<SpuSaleAttributeInfoDTO> spuSaleAttrListCheckBySku = skuService.getSpuSaleAttrListCheckBySku(skuId, skuInfoDTO.getSpuId());
        productDetailDTO.setSpuSaleAttrList(spuSaleAttrListCheckBySku);
        // 5. 查询销售属性的取值组合 和 skuId的映射关系
        // 幻夜星河+ 8+128 --> 282
        // 3770|3773 : 14
        Map<String, Long> skuValueIdsMap = spuService.getSkuValueIdsMap(skuInfoDTO.getSpuId());
        // 在java中把对象转为 json
        // 1. 序列化 JSON.toJSONString
        // 2. 反序列化 JSON.parseObject
        String skuValueIdsMapJson = JSON.toJSONString(skuValueIdsMap);
        productDetailDTO.setValuesSkuJson(skuValueIdsMapJson);
        // 6. 获取分类信息
        CategoryHierarchyDTO categoryViewByCategoryId = categoryService.getCategoryViewByCategoryId(skuInfoDTO.getThirdLevelCategoryId());
        productDetailDTO.setCategoryHierarchy(categoryViewByCategoryId);
        // 7. 查询海报信息
        List<SpuPosterDTO> spuPosterBySpuId = spuService.findSpuPosterBySpuId(skuInfoDTO.getSpuId());
        productDetailDTO.setSpuPosterList(spuPosterBySpuId);
        // 8. 查询平台属性以及平台属性对应的属性集合
        List<PlatformAttributeInfoDTO> platformAttrInfoBySku = skuService.getPlatformAttrInfoBySku(skuId);
        if (CollUtil.isNotEmpty(platformAttrInfoBySku)) {
            List<SkuSpecification> skuSpecificationList = platformAttrInfoBySku.stream().map(platformAttributeInfoDTO -> {
                SkuSpecification skuSpecification = new SkuSpecification();
                // 获取平台属性名
                String attrName = platformAttributeInfoDTO.getAttrName();
                // 获取平台属性的值的名称
                if (CollUtil.isEmpty(platformAttributeInfoDTO.getAttrValueList())) {
                    return skuSpecification;
                }
                String valueName = platformAttributeInfoDTO.getAttrValueList().get(0).getValueName();
                skuSpecification.setAttrName(attrName);
                skuSpecification.setAttrValue(valueName);
                return skuSpecification;
            }).collect(Collectors.toList());
            productDetailDTO.setSkuAttrList(skuSpecificationList);
        }
        return productDetailDTO;
    }

}
