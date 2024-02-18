package cn.kiroe.demo.es;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author Kiro
 * @Date 2024/01/24 19:40
 **/
@SpringBootTest
public class ESTest {
    private static String host = "jfl.kiroe.cn";
    private static Integer port = 9200;

    private static RestHighLevelClient client;

    static {
        HttpHost http = new HttpHost(host, port, "http");
        RestClientBuilder builder = RestClient.builder(http);
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(builder);
        client = restHighLevelClient;
    }
      void testES() {
        System.out.println("client = " + client);

    }
}
