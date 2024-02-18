package cn.kiroe.mall.product.service.impl;

import cn.kiroe.mall.product.converter.dto.SaleAttributeInfoConverter;
import cn.kiroe.mall.product.converter.dto.SpuInfoConverter;
import cn.kiroe.mall.product.converter.dto.SpuInfoPageConverter;
import cn.kiroe.mall.product.converter.param.SpuInfoParamConverter;
import cn.kiroe.mall.product.dto.*;
import cn.kiroe.mall.product.mapper.*;
import cn.kiroe.mall.product.model.*;
import cn.kiroe.mall.product.query.SpuInfoParam;
import cn.kiroe.mall.product.service.SpuService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import io.github.linpeilie.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author Kiro
 * @Date 2024/01/18 22:43
 **/
@Service
@RequiredArgsConstructor
public class SpuServiceImpl implements SpuService {
    private final Converter converter;
    private final SpuInfoParamConverter spuInfoParamConverter;
    private final SpuInfoPageConverter spuInfoPageConverter;
    private final SpuInfoMapper spuInfoMapper;
    private final SpuInfoConverter spuInfoConverter;
    private final SpuSaleAttrInfoMapper spuSaleAttrInfoMapper;
    private final SaleAttrInfoMapper saleAttrInfoMapper;
    private final SaleAttributeInfoConverter saleAttributeInfoConverter;
    private final SpuImageMapper spuImageMapper;
    private final SpuSaleAttrValueMapper spuSaleAttrValueMapper;
    private final SkuSaleAttrValueMapper skuSaleAttrValueMapper;
    private final SpuPosterMapper spuPosterMapper;


    @Override
    public List<SaleAttributeInfoDTO> getSaleAttrInfoList() {
        List<SaleAttributeInfo> saleAttributeInfos = saleAttrInfoMapper.selectList(null);
        return saleAttributeInfoConverter.saleAttributeInfoPOs2DTOs(saleAttributeInfos);
    }

    /**
     * 根据分类获取 spu信息
     *
     * @param pageParam
     * @param category3Id
     * @return
     */
    @Override
    public SpuInfoPageDTO getSpuInfoPage(final Page<SpuInfo> pageParam, final Long category3Id) {
        Page<SpuInfo> spuInfoPage = spuInfoMapper.selectPage(pageParam,
                new LambdaQueryWrapper<SpuInfo>().eq(SpuInfo::getThirdLevelCategoryId, category3Id));
        return spuInfoPageConverter.spuInfoPage2PageDTO(spuInfoPage);
    }

    /**
     * 保存spu信息
     *
     * @param spuInfoParam
     */
    @Override
    @Transactional
    public void saveSpuInfo(final SpuInfoParam spuInfoParam) {
        // 1. 插入spuinfo
        SpuInfo spuInfo = spuInfoParamConverter.spuInfoParam2Info(spuInfoParam);
        spuInfoMapper.insert(spuInfo);
        // 2. 插入spuimage
        List<SpuImage> spuImages = spuInfo.getSpuImageList();
        // 2.1 插入id
        spuImages.forEach(image -> {
            image.setSpuId(spuInfo.getId());
        });
        Db.saveBatch(spuImages);
        // 3. 插入spu_poster
        List<SpuPoster> spuPosterList = spuInfo.getSpuPosterList();
        // 3.1 插入id
        spuPosterList.forEach(poster -> {
            poster.setSpuId(spuInfo.getId());
        });
        Db.saveBatch(spuPosterList);
        // 4. 插入spu_sale_attr_info,
        List<SpuSaleAttributeInfo> spuSaleAttributeInfos = spuInfo.getSpuSaleAttributeInfoList();
        spuSaleAttributeInfos.forEach(info -> {
            info.setSpuId(spuInfo.getId());
        });
        Db.saveBatch(spuSaleAttributeInfos);
        // 5. 插入spu_sale_attr_vale
        // 5.1 转换为 有id的 spu_attr_value, 将所有value都插入 spu_id,spu_sale_attr_id,
        // 获取所有value
        List<SpuSaleAttributeValue> spuSaleAttributeValues = new ArrayList<>();
        spuSaleAttributeInfos.forEach(info -> {
            info.getSpuSaleAttrValueList().forEach(value -> {
                value.setSpuId(info.getSpuId());
                value.setSpuSaleAttrId(info.getId());
            });
            spuSaleAttributeValues.addAll(info.getSpuSaleAttrValueList());
        });
        // 5.2 最后插入value
        Db.saveBatch(spuSaleAttributeValues);
    }

    @Override
    public List<SpuImageDTO> getSpuImageList(final Long spuId) {
        List<SpuImage> spuImages = spuImageMapper.selectBySpuId(spuId);
        return converter.convert(spuImages, SpuImageDTO.class);
    }

    /*
        根据SpuId查询SPU中包含的销售属性以及销售属性值集合
    */
    @Override
    public List<SpuSaleAttributeInfoDTO> getSpuSaleAttrList(final Long spuId) {
        List<SpuSaleAttributeInfo> spuSaleAttributeInfos = spuSaleAttrInfoMapper.selectAllBySpuId(spuId);
        return converter.convert(spuSaleAttributeInfos, SpuSaleAttributeInfoDTO.class);
    }

    @Override
    public List<SpuPosterDTO> findSpuPosterBySpuId(final Long spuId) {
        List<SpuPoster> spuPosters = spuPosterMapper.selectList(new LambdaQueryWrapper<SpuPoster>().eq(SpuPoster::getSpuId, spuId));
        return spuInfoConverter.spuPosterPOs2DTOs(spuPosters);
    }

    @Override
    public Map<String, Long> getSkuValueIdsMap(final Long spuId) {
        // 查询组合列表
        List<SkuSaleAttributeValuePermutation> permutations = skuSaleAttrValueMapper.selectSaleAttrValuesBySpu(spuId);
        return permutations.stream().collect(Collectors.toMap(SkuSaleAttributeValuePermutation::getSkuSaleAttrValuePermutation, SkuSaleAttributeValuePermutation::getSkuId));
    }
}
