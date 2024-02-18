package cn.kiroe.mall.product.service;

import cn.kiroe.mall.product.dto.SaleAttributeInfoDTO;

import java.util.List;

public interface SalesAttributeService {

    /*
          查询所有的销售属性
     */
    List<SaleAttributeInfoDTO> getSaleAttrInfoList();
}
