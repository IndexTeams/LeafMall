package cn.kiroe.demo;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author Kiro
 * @Date 2024/01/25 11:23
 **/
@SpringBootTest
public class BaseTest {
    @Autowired
   public RestHighLevelClient client;

}
