package cn.kiroe.mall.product.converter.param;


import cn.kiroe.mall.product.model.PlatformAttributeInfo;
import cn.kiroe.mall.product.model.PlatformAttributeValue;
import cn.kiroe.mall.product.query.PlatformAttributeParam;
import cn.kiroe.mall.product.query.PlatformAttributeValueParam;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel =  MappingConstants.ComponentModel.SPRING )
public interface PlatformAttributeInfoParamConverter {

    PlatformAttributeInfo attributeInfoParam2Info(PlatformAttributeParam platformAttributeParam);

    PlatformAttributeValue attributeValueParam2AttributeValue(PlatformAttributeValueParam platformAttributeValueParam);

}
