package cn.kiroe.mall.search.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.kiroe.mall.common.constant.RedisConst;
import cn.kiroe.mall.product.dto.CategoryHierarchyDTO;
import cn.kiroe.mall.product.dto.PlatformAttributeInfoDTO;
import cn.kiroe.mall.product.dto.SkuInfoDTO;
import cn.kiroe.mall.product.dto.TrademarkDTO;
import cn.kiroe.mall.search.client.ProductApiClient;
import cn.kiroe.mall.search.converter.GoodsConverter;
import cn.kiroe.mall.search.dto.SearchResponseAttrDTO;
import cn.kiroe.mall.search.dto.SearchResponseDTO;
import cn.kiroe.mall.search.dto.SearchResponseTmDTO;
import cn.kiroe.mall.search.model.Goods;
import cn.kiroe.mall.search.model.SearchAttr;
import cn.kiroe.mall.search.param.SearchParam;
import cn.kiroe.mall.search.repository.GoodsRepository;
import cn.kiroe.mall.search.service.SearchService;
import cn.kiroe.mall.search.util.GoodsConst;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregate;
import co.elastic.clients.elasticsearch._types.aggregations.Aggregation;
import co.elastic.clients.elasticsearch._types.aggregations.Buckets;
import co.elastic.clients.elasticsearch._types.aggregations.LongTermsBucket;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Highlight;
import co.elastic.clients.util.ObjectBuilder;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.redisson.api.RScoredSortedSet;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;


/**
 * @Author Kiro
 * @Date 2024/01/26 20:05
 **/
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final ProductApiClient productApiClient;
    private final GoodsRepository goodsRepository;
    private final RedissonClient redissonClient;
    private final ElasticsearchClient esClient;
    private final GoodsConverter goodsConverter;

    /**
     * 用于构建 filter查询语句
     *
     * @param searchParam
     * @return
     */
    private static ArrayList<Query> buildFilterQuery(final SearchParam searchParam) {
        ArrayList<Query> filterQuery = new ArrayList<>();
        if (searchParam.getFirstLevelCategoryId() != null) {
            filterQuery.add(QueryBuilders.term(t -> t.field(GoodsConst.FIRST_LEVEL_CATEGORY_ID).value(searchParam.getFirstLevelCategoryId())));
        }
        if (searchParam.getSecondLevelCategoryId() != null) {
            filterQuery.add(QueryBuilders.term(t -> t.field(GoodsConst.SECOND_LEVEL_CATEGORY_ID).value(searchParam.getSecondLevelCategoryId())));
        }
        if (searchParam.getThirdLevelCategoryId() != null) {
            filterQuery.add(QueryBuilders.term(t -> t.field(GoodsConst.THIRD_LEVEL_CATEGORY_ID).value(searchParam.getThirdLevelCategoryId())));
        }
        if (StrUtil.isNotBlank(searchParam.getTrademark())) {
            filterQuery.add(QueryBuilders.term(t -> t.field(GoodsConst.TM_ID).value(searchParam.getTrademark().split(":")[0])));
        }
        // 循环获取 设置搜索情况,还没循环
        if (searchParam.getProps() != null) {
            for (final String prop : searchParam.getProps()) {
                // 分别获取id和value
                String[] split = prop.split(":");
                String attrId = split[0];
                String attrValue = split[1];
                filterQuery.add(QueryBuilders.nested(
                        n -> n.path(GoodsConst.ATTRS).query(q -> q.bool(b -> b.must(
                                QueryBuilders.term(t -> t.field(GoodsConst.ATTR_ID_FIELD).value(attrId)),
                                QueryBuilders.term(t -> t.field(GoodsConst.ATTR_VALUE_FIELD).value(attrValue))
                        )))
                ));
            }
        }
        return filterQuery;
    }

    /**
     * 用于转换获取高亮goodsList
     *
     * @param searchResponse
     * @return
     */
    private static List<Goods> getHighLightGoodsList(final SearchResponse<Goods> searchResponse) {
        List<Goods> goodsList = searchResponse.hits().hits().stream().map(h -> {
            Goods goods = h.source();
            // 设置高亮
            StringBuilder stringBuilder = new StringBuilder();
            List<String> titleList = h.highlight().get(GoodsConst.TITLE);
            if (titleList == null) {
                return goods;
            }
            titleList.forEach(stringBuilder::append);
            assert goods != null;
            goods.setTitle(stringBuilder.toString());
            return goods;
        }).toList();
        return goodsList;
    }

    /**
     * 用于 属性DTOS
     *
     * @param attrsAggbuckets
     * @return
     */
    private static List<SearchResponseAttrDTO> getSearchResponseAttrDTOS(final Buckets<LongTermsBucket> attrsAggbuckets) {
        List<SearchResponseAttrDTO> searchAttrDTOList = attrsAggbuckets.array().stream().map(b -> {
            SearchResponseAttrDTO searchResponseAttrDTO = new SearchResponseAttrDTO();
            searchResponseAttrDTO.setAttrId(b.key());
            searchResponseAttrDTO.setAttrName(b.aggregations().get(GoodsConst.ATTR_NAME_AGG).sterms().buckets().array().get(0).key().stringValue());
            searchResponseAttrDTO.setAttrValueList(b.aggregations().get(GoodsConst.ATTR_VALUE_AGG).sterms().buckets().array().stream().map(i -> i.key().stringValue()).toList());
            return searchResponseAttrDTO;
        }).toList();
        return searchAttrDTOList;
    }

    /**
     * 用于获取tmDTO
     *
     * @param tmIdAggBuckets
     * @return
     */
    private static List<SearchResponseTmDTO> getSearchResponseTmDTOS(final Buckets<LongTermsBucket> tmIdAggBuckets) {
        // 将tmIdBuckets转为对应值
        List<SearchResponseTmDTO> searchResponseTmDTOList = tmIdAggBuckets.array().stream().map(b -> {
            SearchResponseTmDTO searchResponseTmDTO = new SearchResponseTmDTO();
            searchResponseTmDTO.setTmId(b.key());
            searchResponseTmDTO.setTmName(b.aggregations().get(GoodsConst.TM_NAME_AGG).sterms().buckets().array().get(0).key().stringValue());
            searchResponseTmDTO.setTmLogoUrl(b.aggregations().get(GoodsConst.TM_LOGO_URL_AGG).sterms().buckets().array().get(0).key().stringValue());
            return searchResponseTmDTO;
        }).toList();
        return searchResponseTmDTOList;
    }

    /**
     * 构建聚合条件
     *
     * @return
     */
    private HashMap<String, Aggregation> buildAggMap() {

        HashMap<String, Aggregation> aggMap = new HashMap<>();
        // size为显示的条数，默认为10
        aggMap.put(GoodsConst.TM_ID_AGG, Aggregation.of(agg -> {
                            HashMap<String, Aggregation> tmAggMap = new HashMap<>();
                            tmAggMap.put(GoodsConst.TM_NAME_AGG, Aggregation.of(a -> a.terms(t -> t.field(GoodsConst.TM_NAME))));
                            tmAggMap.put(GoodsConst.TM_LOGO_URL_AGG, Aggregation.of(a -> a.terms(t -> t.field(GoodsConst.TM_LOGO_URL))));
                            return agg.terms(t -> t.field(GoodsConst.TM_ID))
                                      .aggregations(tmAggMap);
                        }
                )
        );
        aggMap.put(GoodsConst.ATTRS_AGG,
                Aggregation.of(agg -> agg.nested(n -> n.path(GoodsConst.ATTRS))
                                         .aggregations(GoodsConst.ATTR_ID_AGG, a -> {
                                                     HashMap<String, Aggregation> map = new HashMap<>();
                                                     map.put(GoodsConst.ATTR_NAME_AGG, Aggregation.of(agg3 -> agg3.terms(t -> t.field(GoodsConst.ATTR_NAME_FIELD))));
                                                     map.put(GoodsConst.ATTR_VALUE_AGG, Aggregation.of(agg3 -> agg3.terms(t -> t.field(GoodsConst.ATTR_VALUE_FIELD))));
                                                     return a.terms(t -> t.field(GoodsConst.ATTR_ID_FIELD))
                                                             .aggregations(map);
                                                 }
                                         )
                )
        );
        return aggMap;
    }

    /**
     * 用于关键整体条件
     *
     * @param searchParam
     * @return
     */
    private Function<SearchRequest.Builder, ObjectBuilder<SearchRequest>> buildDSL(final SearchParam searchParam) {
        String keyword = searchParam.getKeyword();
        Integer pageSize = searchParam.getPageSize();
        Integer pageNo = searchParam.getPageNo();
        Integer from = (pageNo - 1) * pageSize;
        String orderStr = searchParam.getOrder();
        SortOrder sortOrder = SortOrder.Asc;
        String orderField = GoodsConst.HOT_SCORE;
        if(StrUtil.isNotBlank(orderStr)){// 为2:asc
            String[] split = orderStr.split(":");
            String fieldNum = split[0];
            orderField = "1".equals(fieldNum)?GoodsConst.HOT_SCORE:GoodsConst.PRICE;
            String orderString = split[1];
            sortOrder = SortOrder.Asc.jsonValue().equals(orderString)?SortOrder.Asc:SortOrder.Desc;
        }
        Object[] orderArr = new Object[2];
        orderArr[0] = orderField;
        orderArr[1] = sortOrder;
        return s -> s.index(GoodsConst.GOODS_INDEX)
                     .query(
                             q -> q.bool(
                                     b -> b.must(StrUtil.isBlank(keyword) ? QueryBuilders.matchAll().build()._toQuery() :
                                             QueryBuilders.match(
                                                     ma -> ma.field(GoodsConst.TITLE).query(keyword)
                                             )
                                     ).filter(buildFilterQuery(searchParam))
                             )
                     ).from(from).size(pageSize)
                     .sort(so -> so.field(f -> f.field((String) orderArr[0]).order((SortOrder) orderArr[1])))
                     .aggregations(buildAggMap())
                     .highlight(StrUtil.isBlank(keyword) ? null :
                             Highlight.of(h -> h.fields(GoodsConst.TITLE, f -> f.preTags(GoodsConst.PRE_TAGS).postTags(GoodsConst.POST_TAGS))))
                     .source(source -> source.filter(filter -> filter.includes(GoodsConst.ID, GoodsConst.DEFAULT_IMG, GoodsConst.TITLE, GoodsConst.PRICE)));
    }

    /**
     * 上架
     * 1. 查询出的商品信息
     * 基本信息,id,名称，价格，默认图片
     * 分类信息,1.2.3
     * 品牌信息 品牌id，品牌名称，品牌图片
     * 平台属性集合 平台属性id，名称，值
     * 2. 保存至es中
     *
     * @param skuId
     */
    @Override
    public void upperGoods(final Long skuId) {
        // 1.查询出商品的基本信息
        SkuInfoDTO skuInfoDTO = productApiClient.getSkuInfo(skuId);
        // 2. 分类
        CategoryHierarchyDTO categoryView = productApiClient.getCategoryView(skuInfoDTO.getThirdLevelCategoryId());
        // 3. 品牌
        TrademarkDTO trademark = productApiClient.getTrademark(skuInfoDTO.getTmId());
        // 4. 平台属性
        List<PlatformAttributeInfoDTO> attrList = productApiClient.getAttrList(skuId);
        // 5. 放入对象中
        Goods goods = toGoods(skuInfoDTO, categoryView, trademark, attrList);
        // 6. 保存至es
        assert goods != null;
        goodsRepository.save(goods);
    }

    private Goods toGoods(final SkuInfoDTO skuInfoDTO, final CategoryHierarchyDTO categoryView, final TrademarkDTO trademark, final List<PlatformAttributeInfoDTO> attrList) {
        // sku
        Goods goods = goodsConverter.skuInfoDTO2GoodsPO(skuInfoDTO);
        goods.setTitle(skuInfoDTO.getSkuName());
        goods.setDefaultImg(skuInfoDTO.getSkuDefaultImg());
        // 分类
        goods.setFirstLevelCategoryId(categoryView.getFirstLevelCategoryId());
        goods.setFirstLevelCategoryName(categoryView.getFirstLevelCategoryName());
        goods.setSecondLevelCategoryId(categoryView.getSecondLevelCategoryId());
        goods.setSecondLevelCategoryName(categoryView.getSecondLevelCategoryName());
        goods.setThirdLevelCategoryId(categoryView.getThirdLevelCategoryId());
        goods.setThirdLevelCategoryName(categoryView.getThirdLevelCategoryName());
        // trademark
        goods.setTmId(trademark.getId());
        goods.setTmName(trademark.getTmName());
        goods.setTmLogoUrl(trademark.getLogoUrl());
        // attrList
        List<SearchAttr> searchAttrList = attrList.stream().map(
                attr -> {
                    SearchAttr searchAttr = new SearchAttr();
                    searchAttr.setAttrId(attr.getId());
                    searchAttr.setAttrName(attr.getAttrName());
                    // 只会有一个
                    searchAttr.setAttrValue(attr.getAttrValueList().get(0).getValueName());
                    return searchAttr;
                }
        ).toList();
        goods.setAttrs(searchAttrList);
        return goods;
    }

    @Override
    public void lowerGoods(final Long skuId) {
        goodsRepository.deleteById(skuId);
    }

    @Override
    public void incrHotScore(final Long skuId) {
        // 这种做法最es的压力大，所以可考虑采用批量更新的方式
        // 那如何批量更新呢
        // 可以暂时把热度保存到redis,每次+1,就给redis中的热度+1
        // 查看当前的热度是不是100的整数
        // map ,string,
        // 这里因为要使用 es查询doc，所以不能只 放入redis中
        RScoredSortedSet<Object> scoredSortedSet = redissonClient.getScoredSortedSet(RedisConst.HOT_SCORE);
        Double currentScore = scoredSortedSet.addScore(skuId, 1);
        // 每到2更新一次
        if (currentScore.intValue() % 2 != 0) {
            return;
        }
        Optional<Goods> optionalGoods = goodsRepository.findById(skuId);
        Goods goods = optionalGoods.get();
        goods.setHotScore(goods.getHotScore() + 1);
        goodsRepository.save(goods);
    }

    /**
     * 条件查询相关代码
     *
     * @param searchParam
     * @return
     */
    @Override
    @SneakyThrows
    public SearchResponseDTO search(final SearchParam searchParam) {
        // 1. 构建DSL语句,发起请求，去搜索
        assert searchParam != null;
        SearchResponse<Goods> searchResponse = esClient.search(buildDSL(searchParam), Goods.class);
        // 2. 解析搜索结果为我们需要的对象
        return parseResponse(searchResponse, searchParam);
    }

    private SearchResponseDTO parseResponse(final SearchResponse<Goods> searchResponse, final SearchParam searchParam) {
        SearchResponseDTO searchResponseDTO = new SearchResponseDTO();
        Map<String, Aggregate> aggregations = searchResponse.aggregations();
        Buckets<LongTermsBucket> tmIdAggBuckets = aggregations.get(GoodsConst.TM_ID_AGG).lterms().buckets();
        // 获取TmDTOList
        List<SearchResponseTmDTO> searchResponseTmDTOList = getSearchResponseTmDTOS(tmIdAggBuckets);
        searchResponseDTO.setTrademarkList(searchResponseTmDTOList);
        Buckets<LongTermsBucket> attrsAggbuckets = aggregations.get(GoodsConst.ATTRS_AGG).nested().aggregations().get(GoodsConst.ATTR_ID_AGG).lterms().buckets();
        // 将attrsAgg转为对应的值
        List<SearchResponseAttrDTO> searchAttrDTOList = getSearchResponseAttrDTOS(attrsAggbuckets);
        searchResponseDTO.setAttrsList(searchAttrDTOList);
        // 将goods放入，并设置高亮
        List<Goods> goodsList = getHighLightGoodsList(searchResponse);
        searchResponseDTO.setGoodsList(goodsConverter.goodsPOs2DTOs(goodsList));
        assert searchResponse.hits().total() != null;
        // 设置分页信息
        searchResponseDTO.setTotal(searchResponse.hits().total().value());
        searchResponseDTO.setPageSize(searchParam.getPageSize());
        searchResponseDTO.setPageNo(searchParam.getPageNo());
        searchResponseDTO.setTotalPages((long) Math.ceil((double) searchResponseDTO.getTotal() / searchResponseDTO.getPageSize()));
        return searchResponseDTO;
    }


}
