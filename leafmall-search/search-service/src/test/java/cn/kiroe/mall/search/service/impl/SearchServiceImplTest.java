package cn.kiroe.mall.search.service.impl;

import cn.kiroe.mall.search.model.Goods;
import cn.kiroe.mall.search.param.SearchParam;
import cn.kiroe.mall.search.service.SearchService;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author Kiro
 * @Date 2024/01/28 10:44
 **/
@SpringBootTest
class SearchServiceImplTest {
    @Autowired
    SearchService searchService;
    @Autowired
    ElasticsearchClient esClient;
      void search() {
        searchService.search(new SearchParam());
    }

      void searchTest() throws IOException {
        SearchResponse<Goods> search = esClient.search(i -> i.index("my_goods").query(q->q.bool(
                b->b.must(QueryBuilders.matchAll().build()._toQuery())
        )), Goods.class);
    }
}