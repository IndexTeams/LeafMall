package cn.kiroe.mall.product.service.impl;

import cn.kiroe.mall.product.converter.dto.TrademarkConverter;
import cn.kiroe.mall.product.converter.dto.TrademarkPageConverter;
import cn.kiroe.mall.product.converter.dto.TrademarkPageConverterImpl;
import cn.kiroe.mall.product.dto.TrademarkDTO;
import cn.kiroe.mall.product.dto.TrademarkPageDTO;
import cn.kiroe.mall.product.mapper.CategoryTrademarkMapper;
import cn.kiroe.mall.product.mapper.TrademarkMapper;
import cn.kiroe.mall.product.model.CategoryTrademark;
import cn.kiroe.mall.product.model.Trademark;
import cn.kiroe.mall.product.query.TrademarkParam;
import cn.kiroe.mall.product.service.TrademarkService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.linpeilie.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author Kiro
 * @Date 2024/01/18 20:19
 **/
@Service
@RequiredArgsConstructor
public class TrademarkServiceImpl implements TrademarkService {
    private final TrademarkMapper trademarkMapper;
    private final TrademarkPageConverter trademarkPageConverter;
    private final TrademarkConverter trademarkConverter;
    private final CategoryTrademarkMapper categoryTrademarkMapper;
    private final Converter converter;
    @Override
    public TrademarkDTO getTrademarkByTmId(final Long tmId) {
        Trademark trademark = trademarkMapper.selectById(tmId);
        return trademarkConverter.trademarkPO2DTO(trademark);
    }

    @Override
    public TrademarkPageDTO getPage(final Page<Trademark> pageParam) {
        Page<Trademark> trademarkPage = trademarkMapper.selectPage(pageParam, null);
        return trademarkPageConverter.tradeMarkPagePO2PageDTO(trademarkPage);
    }

    @Override
    public void save(final TrademarkParam trademarkParam) {
        Trademark trademark = trademarkConverter.trademarkParam2Trademark(trademarkParam);
        trademarkMapper.insert(trademark);
    }

    @Override
    public void updateById(final TrademarkParam trademarkParam) {
        Trademark trademark = trademarkConverter.trademarkParam2Trademark(trademarkParam);
        int i = trademarkMapper.updateById(trademark);
        if (i != 1){
            new RuntimeException("更新失败");
        }
    }

    @Override
    public void removeById(final Long id) {
        int i = trademarkMapper.deleteById(id);
        if (i != 1){
            new RuntimeException("删除失败");
        }

    }

    /**
     *获取当前未被3级分类关联的所以品牌
     * @param thirdLevelCategoryId
     * @return
     */
    @Override
    public List<TrademarkDTO> findUnLinkedTrademarkList(final Long thirdLevelCategoryId) {
        // 获取 当前所属的品牌的id
        List<CategoryTrademark> categoryTrademarks = categoryTrademarkMapper.selectByThirdLevelCategoryId(thirdLevelCategoryId);
        // 转换为map
        Set<Long> linkedTrademarkset = categoryTrademarks.stream().map(CategoryTrademark::getTrademarkId).collect(Collectors.toSet());
        // 获取其他 未关联的品牌,获取所有，然后排除
        List<Trademark> trademarkAllList = trademarkMapper.selectList(null);
        // 筛选未被选的
        List<Trademark> unLinkedTrademarkList = trademarkAllList.stream().filter(t -> !linkedTrademarkset.contains(t.getId())).collect(Collectors.toList());
        return converter.convert(unLinkedTrademarkList, TrademarkDTO.class);
    }
}
