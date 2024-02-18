package cn.kiroe.demo.es;

import cn.kiroe.demo.BaseTest;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Result;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author Kiro
 * @Date 2024/01/25 17:33
 **/
public class HighLevelQueryTest extends BaseTest {
    /**
     * POST http://jfl.kiroe.cn:9200/bank/_search
     * Content-Type: application/json
     * <p>
     * {
     * "query": {
     * "match_all": {}
     * },
     * "from": 0,
     * "size": 10
     * }
     */

      public void testMatchAll() throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        // 填充query
        sourceBuilder.query(QueryBuilders.matchAllQuery());
        // 填充from参数
        sourceBuilder.from(3);
        // 填充size参数
        sourceBuilder.size(3);
        searchRequest.indices("bank").source(sourceBuilder);
        SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = response.getHits();
        SearchHit[] hits1 = hits.getHits();
        // System.out.println("hits1 = " + Arrays.toString(hits1));
        SearchHit searchHit = hits1[0];
        String sourceAsString = searchHit.getSourceAsString();
        System.out.println("sourceAsString = " + sourceAsString);
        System.out.println("searchHit.getSourceAsMap() = " + searchHit.getSourceAsMap());

    }

      void testESJava() throws IOException {
        // 获取客户端连接
        RestClient restClient = RestClient.builder(HttpHost.create("http://jfl.kiroe.cn:9200")).build();
        // 创建 传输器
        RestClientTransport restClientTransport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        // 创建es客户端
        ElasticsearchClient esClient = new ElasticsearchClient(restClientTransport);
        // 查询index
        // 可直接转为对应的 对象，方便！
        GetResponse<HashMap> response = esClient.get(builder -> builder.index("bank").id("1"), HashMap.class);

        HashMap source = response.source();
        System.out.println("source = " + source);
        System.out.println("response = " + response);

        // 测试 query
        esClient.search(s -> s.query(q -> q.matchAll(m -> m.queryName("id"))), Map.class);
        // 测试删除
        DeleteResponse deleteResponse = esClient.delete(d -> d.index("user").id("3"));
        System.out.println("deleteResponse = " + deleteResponse);
        Result result = deleteResponse.result();
        System.out.println("result = " + result);

    }

      void testQuery() {

    }
}
