package cn.kiroe.demo.es;

import cn.kiroe.demo.BaseTest;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

/**
 * @Author Kiro
 * @Date 2024/01/26 11:09
 **/
public class HighLevelQueryTest2 extends BaseTest {
    private static ElasticsearchClient esClient;

    // 查询money 在
    static {
        // 获取客户端连接
        RestClient restClient = RestClient.builder(HttpHost.create("http://jfl.kiroe.cn:9200")).build();
        // 创建 转换器
        RestClientTransport restClientTransport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        // 创建es客户端
        esClient = new ElasticsearchClient(restClientTransport);

    }

    /**
     * ### 综合练习
     * # 查询money ,gender,address
     * # addres 需要分数
     * GET http://jfl.kiroe.cn:9200/bank/_search
     * Content-Type: application/json
     * <p>
     * {
     * "query": {
     * "bool": {
     * "filter": [
     * {
     * "match": {
     * "address": "mill"
     * }
     * }
     * ],
     * "must": [
     * {
     * "match": {
     * "gender": "M"
     * }
     * },
     * {
     * "range": {
     * "balance": {
     * "gte": 9800,
     * "lte": 9813
     * }
     * }
     * }
     * ]
     * }
     * }
     * }
     */
      void testBool() throws IOException {
        // 2. 设置索引
        SearchRequest searchRequest = new SearchRequest().indices("bank");
        // 设置query
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(new BoolQueryBuilder());
        searchRequest.source(sourceBuilder);
        // 1. 发生请求
        client.search(searchRequest
                , RequestOptions.DEFAULT);
    }

      void testBoolEsClient() throws IOException {
        SearchResponse<HashMap> response = esClient
                .search(s -> s.index("bank").query(
                                q -> q.bool(
                                        b -> b.filter(
                                                f -> f.match(
                                                        m -> m.field("address")
                                                              .query("mill")
                                                )
                                        )
                                )
                        )
                        , HashMap.class);
        System.out.println("response = " + response);
    }

      void testAggs() throws IOException {
        SearchResponse<HashMap> response =
                esClient.search(s -> s.index("bank")
                                      .query(
                                              q -> q.matchAll(
                                                      m -> m.queryName(null)))
                                      .aggregations("sum_balance",
                                              aggs -> aggs.sum(
                                                      sum -> sum.field("balance")))
                                      .aggregations("avg_balance",
                                              aggs -> aggs.avg(
                                                      a -> a.field("balance")))
                                      .aggregations("min_b",
                                              a -> a.min(
                                                      m -> m.field("balance")))
                        , HashMap.class);
        System.out.println("response = " + response);
    }

      void testAggsBulk() throws IOException {
        SearchResponse<HashMap> response = esClient.search(s -> s.index("bank").query(
                        q -> q.matchAll(m -> m.queryName(null))
                ).aggregations("age_bucket",
                        aggs -> aggs.terms(t -> t.field("age"))
                                    .aggregations("balance_bucket",
                                            a -> a.terms(t -> t.field("balance"))))
                , HashMap.class);
        System.out.println("response = " + response);
    }

    /**
     * 高亮查询
     * {
     * "query": {
     * "match": {
     * "address": "road"
     * }
     * },
     * "highlight": {
     * "fields": {
     * "address": {
     * "pre_tags": "<font color=red>",
     * "post_tags": "</font>"
     * }
     * }
     * }
     * }
     */
      void testHighListh() throws IOException {
        SearchResponse<HashMap> search = esClient.search(s -> s.query(
                q -> q.match(
                        m -> m.field("address").query("road")
                )
        ).highlight(h -> h.fields("address",
                f -> f.preTags("<font color=red>")
                      .postTags("</font>"))), HashMap.class);
    }


}
