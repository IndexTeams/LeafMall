package cn.kiroe.demo.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Kiro
 * @Date 2024/01/25 11:20
 **/
@Configuration
public class ESConfig {

    /**
     * 向容器中组成一个ES的客户端对象
     */
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(RestClient.builder(new HttpHost("jfl.kiroe.cn", 9200, "http")));
        return restHighLevelClient;
    }

}
