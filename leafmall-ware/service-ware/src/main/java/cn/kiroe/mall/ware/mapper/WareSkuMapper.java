package cn.kiroe.mall.ware.mapper;


import cn.kiroe.mall.ware.model.WareSku;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * @param
 * @return
 */
public interface WareSkuMapper extends BaseMapper<WareSku> {

    // 通过商品Id查询商品库存
    public Integer selectStockBySkuid(String skuid);

    // 锁定库存
    public int selectStockBySkuidForUpdate(WareSku wareSku);

    // 扣减库存
    public void incrStockLocked(WareSku wareSku);
}
