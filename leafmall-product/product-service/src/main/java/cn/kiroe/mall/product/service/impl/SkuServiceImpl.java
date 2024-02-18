package cn.kiroe.mall.product.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.kiroe.mall.common.constant.RedisConst;
import cn.kiroe.mall.product.converter.dto.PlatformAttributeInfoConverter;
import cn.kiroe.mall.product.converter.dto.SkuInfoConverter;
import cn.kiroe.mall.product.converter.dto.SkuInfoPageConverter;
import cn.kiroe.mall.product.converter.dto.SpuInfoConverter;
import cn.kiroe.mall.product.converter.param.SkuInfoParamConverter;
import cn.kiroe.mall.product.dto.PlatformAttributeInfoDTO;
import cn.kiroe.mall.product.dto.SkuInfoDTO;
import cn.kiroe.mall.product.dto.SkuInfoPageDTO;
import cn.kiroe.mall.product.dto.SpuSaleAttributeInfoDTO;
import cn.kiroe.mall.product.mapper.*;
import cn.kiroe.mall.product.model.*;
import cn.kiroe.mall.product.mq.producer.SkuProducer;
import cn.kiroe.mall.product.query.SkuInfoParam;
import cn.kiroe.mall.product.service.SkuService;
import cn.kiroe.mall.redis.annotation.RedisCache;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author Kiro
 * @Date 2024/01/19 16:07
 **/
@Service
@RequiredArgsConstructor
public class SkuServiceImpl implements SkuService {
    private final SkuInfoMapper skuInfoMapper;
    private final SkuImageMapper skuImageMapper;
    private final SpuImageMapper spuImageMapper;
    private final SkuInfoParamConverter skuInfoParamConverter;
    private final SkuInfoPageConverter skuInfoPageConverter;
    private final SkuInfoConverter skuInfoConverter;
    private final SpuSaleAttrInfoMapper spuSaleAttrInfoMapper;
    private final SpuInfoConverter spuInfoConverter;
    private final SkuPlatformAttrValueMapper skuPlatformAttrValueMapper;
    private final PlatformAttrInfoMapper platformAttrInfoMapper;
    private final PlatformAttrValueMapper platformAttrValueMapper;
    private final PlatformAttributeInfoConverter platformAttributeInfoConverter;
    private final RedissonClient redissonClient;
    private final SkuSaleAttrValueMapper skuSaleAttrValueMapper;
    private final SkuProducer skuProducer;

    /**
     * 1. 保存SKU基本信息
     * 2. 保存SKU图片
     * 3. 保存销售属性信息
     * 4. 保存平台属性值
     *
     * @param skuInfoParam
     */
    @Override
    public void saveSkuInfo(final SkuInfoParam skuInfoParam) {


        SkuInfo skuInfo = skuInfoParamConverter.SkuInfoParam2Info(skuInfoParam);
        // // 判断是否已经有 该属性的商品上架，如果有则无法上架
        // boolean exists = true;
        // for (final SkuSaleAttributeValue value : skuInfo.getSkuSaleAttributeValueList()) {
        //     boolean existsAttrValue = skuSaleAttrValueMapper.exists(
        //             new LambdaQueryWrapper<SkuSaleAttributeValue>().eq(SkuSaleAttributeValue::getSpuId,
        //                     skuInfo.getSpuId()).eq(SkuSaleAttributeValue::getSpuSaleAttrValueId, value.getSpuSaleAttrValueId())
        //     );
        //     // 当有不存在的时候能插入
        //     if(!existsAttrValue){
        //         exists = false;
        //         break;
        //     }
        // }
        // // 如果全部是存在，即不让插入
        // if(exists){
        //     throw  new RuntimeException("已经存在该属性的sku");
        // }

        // 1. 插入sku基本信息
        skuInfoMapper.insert(skuInfo);
        // 2.保存图片
        skuInfo.getSkuImageList().forEach(image -> image.setSkuId(skuInfo.getId()));
        Db.saveBatch(skuInfo.getSkuImageList());
        // 3.保存销售属性信息
        skuInfo.getSkuSaleAttributeValueList().forEach(value -> {
            value.setSkuId(skuInfo.getId());
            value.setSpuId(skuInfoParam.getSpuId());
        });
        Db.saveBatch(skuInfo.getSkuSaleAttributeValueList());
        // 4. 保存平台属性
        skuInfo.getSkuPlatformAttributeValueList().forEach(value -> value.setSkuId(skuInfo.getId()));
        Db.saveBatch(skuInfo.getSkuPlatformAttributeValueList());

    }

    @Override
    public SkuInfoPageDTO getPage(final Page<SkuInfo> pageParam) {
        Page<SkuInfo> skuInfoPage = skuInfoMapper.selectPage(pageParam, null);
        return skuInfoPageConverter.skuInfoPagePO2PageDTO(skuInfoPage);
    }

    @Override
    public void onSale(final Long skuId) {
        skuInfoMapper.update(new LambdaUpdateWrapper<SkuInfo>()
                .set(SkuInfo::getIsSale, 1)
                .eq(SkuInfo::getId, skuId));
        // 2. 添加这个商品至布隆过滤器
        RBloomFilter<Object> bloomFilter = redissonClient.getBloomFilter(RedisConst.SKU_BLOOM_FILTER);
        bloomFilter.add(skuId);
        // 发送上架的消息
        skuProducer.upperGoods(skuId);

    }

    @Override
    public void offSale(final Long skuId) {
        skuInfoMapper.update(new LambdaUpdateWrapper<SkuInfo>()
                .set(SkuInfo::getIsSale, 0)
                .eq(SkuInfo::getId, skuId));
        // 发送下架的消息
        skuProducer.lowerGoods(skuId);
    }

    /**
     * 工具sku获取sku的基本信息
     *
     * @param skuId
     * @return
     */
    @Override
    @RedisCache(prefix = RedisConst.SKUKEY_PREFIX)
    public SkuInfoDTO getSkuInfo(final Long skuId) {
        // 1. 查询基本信息
        SkuInfo skuInfo = skuInfoMapper.selectById(skuId);
        // 2. 对应的图片集合 select * from
        List<SkuImage> skuImages = skuImageMapper.selectList(new LambdaUpdateWrapper<SkuImage>().eq(SkuImage::getSkuId, skuId));

        List<SpuImage> spuImages = null;
        if (CollUtil.isNotEmpty(skuImages)) {
            spuImages = spuImageMapper.selectBatchIds(skuImages.stream().map(SkuImage::getSpuImgId).toList());
        }
        Map<Long, SpuImage> spuImageMap = null;
        if (CollUtil.isNotEmpty(spuImages)) {
            spuImageMap = spuImages.stream().collect(Collectors.toMap(SpuImage::getId, image -> image));
            for (final SkuImage skuImage : skuImages) {
                SpuImage spuImage = spuImageMap.get(skuImage.getSpuImgId());
                skuImage.setImgName(spuImage.getImgName());
                skuImage.setImgUrl(spuImage.getImgUrl());
            }
        }
        // 3. 转化
        skuInfo.setSkuImageList(skuImages);
        return skuInfoConverter.skuInfoPO2DTO(skuInfo);
    }

    @Override
    public SkuInfoDTO getSkuInfoCache(final Long skuId) {
        // 1. 从Redis获取数据
        String key = "SkuServiceImpl:getSkuInfoCache:" + skuId;
        RBucket<SkuInfoDTO> bucket = redissonClient.getBucket(key);
        SkuInfoDTO skuInfoDTO = bucket.get();
        // 2. 如果有数据，那么直接返回
        if (skuInfoDTO != null) {
            return bucket.get();
        }
        // 3. 如果没有数据，则查询mysql
        // 解决击穿问题，使用锁
        RLock lock = redissonClient.getLock(key + ":lock");

        try {
            lock.lock();
            // 双重检查 double check
            // 如果已经有了返回
            SkuInfoDTO skuInfoDTO1 = bucket.get();
            if (skuInfoDTO1 != null) {
                return skuInfoDTO1;
            }
            skuInfoDTO = getSkuInfo(skuId);
        } finally {
            lock.unlock();
        }
        // 4. 查询到后，存入redis
        if (skuInfoDTO == null) {
            // 创建默认的空对象，解决缓存穿透的问题
            skuInfoDTO = new SkuInfoDTO();
        }
        bucket.set(skuInfoDTO);
        // 5. 返回
        return skuInfoDTO;
    }

    @Override
    public BigDecimal getSkuPrice(final Long skuId) {
        SkuInfo skuInfo = skuInfoMapper.selectById(skuId);
        if (skuInfo == null) {
            return null;
        }
        return skuInfo.getPrice();
    }

    // 查询sku所属属性
    @Override
    public List<SpuSaleAttributeInfoDTO> getSpuSaleAttrListCheckBySku(final Long skuId, final Long spuId) {
        List<SpuSaleAttributeInfo> spuSaleAttributeInfos = spuSaleAttrInfoMapper.selectSpuSaleAttrListCheckedBySku(skuId, spuId);
        return spuInfoConverter.spuSaleAttributeInfoPOs2DTOs(spuSaleAttributeInfos);
    }

    /**
     * 获取sku的 平台属性值
     *
     * @param skuId
     * @return
     */
    @Override
    public List<PlatformAttributeInfoDTO> getPlatformAttrInfoBySku(final Long skuId) {
        // 1.查询出sku对应平台属性列表
        List<SkuPlatformAttributeValue> skuPlatformAttributeValues = skuPlatformAttrValueMapper.selectList(new LambdaQueryWrapper<SkuPlatformAttributeValue>().eq(SkuPlatformAttributeValue::getSkuId, skuId));
        if (CollUtil.isEmpty(skuPlatformAttributeValues)) return null;
        // 2.根据平台属性的id 查平台属性
        List<PlatformAttributeInfo> platformAttributeInfos = platformAttrInfoMapper.selectBatchIds(skuPlatformAttributeValues.stream().map(SkuPlatformAttributeValue::getAttrId).toList());
        if (CollUtil.isEmpty(platformAttributeInfos)) return null;
        // 3. 查询 value，并放入 platformAttributeInfos中
        List<PlatformAttributeValue> platformAttributeValues = platformAttrValueMapper.selectBatchIds(skuPlatformAttributeValues.stream().map(SkuPlatformAttributeValue::getValueId).toList());
        if (CollUtil.isEmpty(platformAttributeValues)) return null;
        Map<Long, List<PlatformAttributeValue>> valueMap = platformAttributeValues
                .stream().collect(Collectors.groupingBy(PlatformAttributeValue::getAttrId));
        // 将value的值存入 info中
        platformAttributeInfos.forEach(info -> info.setAttrValueList(valueMap.get(info.getId())));
        return platformAttributeInfoConverter.platformAttributeInfoPOs2DTOs(platformAttributeInfos);
    }


}
