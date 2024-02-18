package cn.kiroe.mall.es.util;

import cn.hutool.extra.spring.SpringUtil;
import co.elastic.clients.elasticsearch.ElasticsearchClient;

/**
 * @Author Kiro
 * @Date 2024/01/27 15:41
 **/
public class ESUtil {
    private static ElasticsearchClient client = SpringUtil.getBean("elasticsearchClient");
    public static ElasticsearchClient getClient(){
        return client;
    }

}
