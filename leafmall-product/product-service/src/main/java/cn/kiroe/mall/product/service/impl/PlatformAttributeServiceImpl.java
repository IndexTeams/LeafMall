package cn.kiroe.mall.product.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.kiroe.mall.product.converter.dto.PlatformAttributeInfoConverter;
import cn.kiroe.mall.product.converter.dto.PlatformAttributeValueConverter;
import cn.kiroe.mall.product.converter.param.PlatformAttributeInfoParamConverter;
import cn.kiroe.mall.product.dto.PlatformAttributeInfoDTO;
import cn.kiroe.mall.product.dto.PlatformAttributeValueDTO;
import cn.kiroe.mall.product.mapper.PlatformAttrInfoMapper;
import cn.kiroe.mall.product.mapper.PlatformAttrValueMapper;
import cn.kiroe.mall.product.model.PlatformAttributeInfo;
import cn.kiroe.mall.product.model.PlatformAttributeValue;
import cn.kiroe.mall.product.query.PlatformAttributeParam;
import cn.kiroe.mall.product.service.PlatformAttributeService;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import io.github.linpeilie.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author Kiro
 * @Date 2024/01/18 11:12
 **/
@Service
@RequiredArgsConstructor
public class PlatformAttributeServiceImpl implements PlatformAttributeService{
    private final PlatformAttrInfoMapper platformAttrInfoMapper;
    private final PlatformAttrValueMapper platformAttrValueMapper;
    private final PlatformAttributeInfoConverter platformAttributeInfoConverter;
    private final PlatformAttributeInfoParamConverter platformAttributeInfoParamConverter;
    private final PlatformAttributeValueConverter valueConverter;
    private final Converter converter;

    /**
     * 获取平台属性，和平台属性值
     *
     * @param firstLevelCategoryId  一级分类id
     * @param secondLevelCategoryId 二级分类id
     * @param thirdLevelCategoryId  三级分类id
     * @return
     */
    @Override
    public List<PlatformAttributeInfoDTO> getPlatformAttrInfoList(final Long firstLevelCategoryId, final Long secondLevelCategoryId, final Long thirdLevelCategoryId) {
        List<PlatformAttributeInfo> platformAttributeInfos = platformAttrInfoMapper
                .selectPlatFormAttrInfoList(firstLevelCategoryId, secondLevelCategoryId, thirdLevelCategoryId);
        return platformAttributeInfoConverter.platformAttributeInfoPOs2DTOs(platformAttributeInfos);
    }

    /**
     * 保存info与value
     * 在info中，1，2，,3级 都可
     * 通过判断是否 有平台属性id,
     *
     * @param
     */
    @Transactional
    @Override
    public void savePlatformAttrInfo(final PlatformAttributeParam attributeParam) {
        Long attrId = attributeParam.getId();
        // 无id表示为 创建
        if (attrId == null) {
            insertPlatformAttrValue(attributeParam);
        } else { // 有id，表示更新,当value为空时为删除
            if(CollUtil.isEmpty(attributeParam.getAttrValueList())){
                platformAttrInfoMapper.deleteById(attrId);
                return;
            }
            alterPlatformAttrValue(attributeParam, attrId);
            // 更新当前 attrId的name
            platformAttrInfoMapper.updateAttrNameById(attrId, attributeParam.getAttrName());
        }
    }

    private void alterPlatformAttrValue(final PlatformAttributeParam attributeParam, final Long attrId) {
        // 删除原来的值
        platformAttrValueMapper.delByAttrIdNotLogic(attrId);
        // 添加attrId
        attributeParam.getAttrValueList().forEach(v-> v.setAttrId(attrId));
        // 转换为 value
        List<PlatformAttributeValue> newValueList = converter.convert(attributeParam.getAttrValueList(), PlatformAttributeValue.class);
        // 插入新的值
        Db.saveBatch(newValueList);
    }

    private void insertPlatformAttrValue(final PlatformAttributeParam attributeParam) {
        PlatformAttributeInfo platformAttributeInfo = platformAttributeInfoParamConverter.attributeInfoParam2Info(attributeParam);
        // 插入至 info中，并获取id
        platformAttrInfoMapper.insert(platformAttributeInfo);
        // 填入id,
        platformAttributeInfo.getAttrValueList().forEach(v -> v.setAttrId(platformAttributeInfo.getId()));
        // value插入至表中
        Db.saveBatch(platformAttributeInfo.getAttrValueList());
    }

    /**
     * 通过
     * @param attrId
     * @return
     */
    @Override
    public List<PlatformAttributeValueDTO> getPlatformAttrValueList(final Long attrId) {
        // 通过attrId获取
        List<PlatformAttributeValue> valueList = platformAttrValueMapper.selectByAttrId(attrId);
        return valueConverter.ValuesPO2DTOs(valueList);
    }
}
