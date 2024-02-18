package cn.kiroe.mall.search;

import cn.kiroe.mall.es.util.ESUtil;
import cn.kiroe.mall.search.model.Goods;
import cn.kiroe.mall.search.repository.GoodsRepository;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.sql.ElasticsearchSqlClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchClients;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author Kiro
 * @Date 2024/01/27 15:15
 **/
@SpringBootTest
class ServiceSearchApplicationTest {
    @Autowired
    ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    GoodsRepository repository;
    @Autowired
    ElasticsearchClient elasticsearchClient;

      void test1() throws IOException {
        Iterable<Goods> all = repository.findAll();
        GetResponse<HashMap> response = elasticsearchClient.get(i -> i.index("goods").id("1"), HashMap.class);
        GetResponse<HashMap> response1 = ESUtil.getClient().get(i -> i.index("goods").id("1"), HashMap.class);
    }

}