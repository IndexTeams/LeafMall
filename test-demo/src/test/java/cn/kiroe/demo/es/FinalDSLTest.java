package cn.kiroe.demo.es;

import cn.kiroe.demo.BaseTest;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import co.elastic.clients.util.ObjectBuilder;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

/**
 * @Author Kiro
 * @Date 2024/01/27 10:21
 **/
public class FinalDSLTest extends BaseTest {
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

    @NotNull
    private static Function<SearchRequest.Builder, ObjectBuilder<SearchRequest>> getMyGoodsDSL() {
        List<Query> filterQueryList = getFilterQueryList();
        return i -> i.index("my_goods")
                     .query(q -> q.bool(
                             b -> b.must(m -> m.match(m1 -> m1.field("title").query("荣耀手机")))
                                   .filter(filterQueryList))
                     );
    }

    @NotNull
    private static List<Query> getFilterQueryList() {
        List<Query> filterQueryList = new ArrayList<>();
        filterQueryList.add(QueryBuilders.term(t -> t.field("thirdLevelCategoryId").value("61")));
        filterQueryList.add(QueryBuilders.term(t -> t.field("tmId").value("3")));
        filterQueryList.add(QueryBuilders.nested(n -> n.path("attrs").query(q -> q.bool(b -> b.must(
                QueryBuilders.term(t -> t.field("attrs.attrValue").value("8G")),
                QueryBuilders.term(t -> t.field("attrs.attrId").value("23"))
        )))));
        return filterQueryList;
    }

      public void testFinalDSL() throws IOException {
        SearchResponse<HashMap> search = esClient.search(
                getMyGoodsDSL()
                , HashMap.class);

    }

      public void testFinalDSL2() {

    }

}
