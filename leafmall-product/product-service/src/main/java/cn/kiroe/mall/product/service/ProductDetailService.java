package cn.kiroe.mall.product.service;

import cn.kiroe.mall.product.dto.ProductDetailDTO;

public interface ProductDetailService {

    /**
     * 获取sku详情信息
     * @param skuId
     * @return
     */
    ProductDetailDTO getItemBySkuId(Long skuId);

}
