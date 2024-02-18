package cn.kiroe.mall.es.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchClients;

/**
 * @Author Kiro
 * @Date 2024/01/27 15:29
 **/
@Configuration
public class ElasticsearchConfig {
    @Value("${spring.elasticsearch.uris}")
    String uri;
    @Bean
    public ElasticsearchClient elasticsearchClient(){
        // 获取客户端连接
        RestClient restClient = RestClient.builder(HttpHost.create(uri)).build();
        // 创建 转换器
        RestClientTransport restClientTransport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        // 创建es客户端
        ElasticsearchClient elasticsearchClient = new ElasticsearchClient(restClientTransport);
        return elasticsearchClient;
    }
}
