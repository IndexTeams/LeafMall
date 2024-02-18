package cn.kiroe.mall.product.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.kiroe.mall.product.dto.TrademarkDTO;
import cn.kiroe.mall.product.dto.TrademarkPageDTO;
import cn.kiroe.mall.product.model.Trademark;
import cn.kiroe.mall.product.query.TrademarkParam;

import java.util.List;

public interface TrademarkService {
    /*
        根据品牌id，查询品牌
     */
    TrademarkDTO getTrademarkByTmId(Long tmId);
    /**
     * 根据分页参数，分页查询品牌列表
     */
    TrademarkPageDTO getPage(Page<Trademark> pageParam);
    /*
         保存品牌
     */
    void save(TrademarkParam trademarkParam);
    /*
          更新品牌
     */
    void updateById(TrademarkParam trademarkParam);
    /*
        删除品牌
     */
    void removeById(Long id);

    /**
     * 获取当前未被三级分类关联的所有品牌
     */
    List<TrademarkDTO> findUnLinkedTrademarkList(Long thirdLevelCategoryId);
}
