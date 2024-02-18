package cn.kiroe.mall.search.util;

import cn.hutool.core.lang.func.LambdaUtil;
import cn.hutool.core.util.StrUtil;
import cn.kiroe.mall.search.model.Goods;
import cn.kiroe.mall.search.model.SearchAttr;

/**
 * @Author Kiro
 * @Date 2024/01/28 12:03
 **/
public class GoodsConst {
    public static final  String GOODS_INDEX = "goods";

    public final static String TITLE = LambdaUtil.getFieldName(Goods::getTitle);
    public final static String HOT_SCORE = LambdaUtil.getFieldName(Goods::getHotScore);
    public final static String ID = LambdaUtil.getFieldName(Goods::getId);
    public final static String DEFAULT_IMG = LambdaUtil.getFieldName(Goods::getDefaultImg);
    public final static String PRICE = LambdaUtil.getFieldName(Goods::getPrice);
    public final static String FIRST_LEVEL_CATEGORY_ID = LambdaUtil.getFieldName(Goods::getFirstLevelCategoryId);
    public final static String SECOND_LEVEL_CATEGORY_ID = LambdaUtil.getFieldName(Goods::getSecondLevelCategoryId);
    public final static String THIRD_LEVEL_CATEGORY_ID = LambdaUtil.getFieldName(Goods::getThirdLevelCategoryId);
    public final static String AGG_SUFFIX = "Agg";
    public final static String TM_NAME = LambdaUtil.getFieldName(Goods::getTmName);
    public final static String TM_NAME_AGG = TM_NAME + AGG_SUFFIX;
    public final static String TM_LOGO_URL = LambdaUtil.getFieldName(Goods::getTmLogoUrl);
    public final static String TM_LOGO_URL_AGG = TM_LOGO_URL + AGG_SUFFIX;
    public final static String TM_ID = LambdaUtil.getFieldName(Goods::getTmId);
    public final static String TM_ID_AGG = TM_ID + AGG_SUFFIX;
    public final static String ATTRS = LambdaUtil.getFieldName(Goods::getAttrs);
    public final static String ATTRS_AGG = ATTRS + AGG_SUFFIX;
    public final static String ATTR_ID = LambdaUtil.getFieldName(SearchAttr::getAttrId);
    public final static String ATTR_ID_AGG = ATTR_ID + AGG_SUFFIX;
    public final static String ATTR_NAME = LambdaUtil.getFieldName(SearchAttr::getAttrName);
    public final static String ATTR_NAME_AGG = ATTR_NAME + AGG_SUFFIX;
    public final static String ATTR_VALUE = LambdaUtil.getFieldName(SearchAttr::getAttrValue);
    public final static String ATTR_VALUE_AGG = ATTR_VALUE + AGG_SUFFIX;
    public final static String NESTED_FIELD_TEMPLATE = "{}.{}";
    public final static String ATTR_ID_FIELD = StrUtil.format(NESTED_FIELD_TEMPLATE, ATTRS, ATTR_ID);
    public final static String ATTR_NAME_FIELD = StrUtil.format(NESTED_FIELD_TEMPLATE, ATTRS, ATTR_NAME);
    public final static String ATTR_VALUE_FIELD = StrUtil.format(NESTED_FIELD_TEMPLATE,ATTRS,ATTR_VALUE);

    public final static String PRE_TAGS = "<span style=color:red>";
    public final static String POST_TAGS = "</span>";
}
