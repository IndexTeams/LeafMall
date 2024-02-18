package cn.kiroe.demo.config;

import cn.kiroe.demo.BaseTest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.cluster.metadata.MappingMetadata;
import org.elasticsearch.xcontent.XContentType;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

/**
 * @Author Kiro
 * @Date 2024/01/25 11:23
 **/
class ESConfigTest extends BaseTest {

    // 1.创建索引
      void testCreateIndex() throws IOException {
        // 获取操作索引的客户端对象
        IndicesClient indicesClient = client.indices();
        // 创建索引的报文
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("stu");
        // 执行创建
        CreateIndexResponse createIndexResponse = indicesClient.create(createIndexRequest, RequestOptions.DEFAULT);
        createIndexResponse.index();
        createIndexResponse.isAcknowledged();
    }

    // 2. 创建索引，并且指定映射
      void testCreateIndexWithMappingsUserJson() throws IOException {
        // 获取操作索引的客户端对象
        IndicesClient indicesClient = client.indices();
        // 创建索引的报文
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("wangdao1");
        String source = "\n" +
                "{\n" +
                "  \"properties\": {\n" +
                "    \"high\": {\n" +
                "      \"type\": \"float\"\n" +
                "    },\n" +
                "    \"weight\": {\n" +
                "      \"type\": \"double\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        createIndexRequest.mapping(source, XContentType.JSON);
        // 执行创建
        CreateIndexResponse createIndexResponse = indicesClient.create(createIndexRequest, RequestOptions.DEFAULT);
        createIndexResponse.index();
        createIndexResponse.isAcknowledged();
    }

    // 3. 查询索引
      void testFindIndex() throws IOException {
        IndicesClient indices = client.indices();
        // 发起请求
        GetIndexRequest getIndexRequest = new GetIndexRequest("wangdao1","bank");
        GetIndexResponse getIndexResponse = indices.get(getIndexRequest, RequestOptions.DEFAULT);
        // 3.解析响应
        getIndexResponse.getAliases();
        Map<String, MappingMetadata> mappings = getIndexResponse.getMappings();
        for (final Map.Entry<String, MappingMetadata> stringMappingMetadataEntry : mappings.entrySet()) {
            System.out.println("stringMappingMetadataEntry.getKey() = " + stringMappingMetadataEntry.getKey());
            System.out.println("stringMappingMetadataEntry.getValue() = " + stringMappingMetadataEntry.getValue().getSourceAsMap());
        }
    }
    // 4. 判断索引是否存在
      void testIndexExists() throws IOException {
        IndicesClient indices = client.indices();
        boolean wangdao = indices.exists(new GetIndexRequest("wangdao"), RequestOptions.DEFAULT);
        System.out.println("wangdao = " + wangdao);

    }
    // 5. 删除索引
      void testDeleteIndex() throws IOException {
        IndicesClient indices = client.indices();
        AcknowledgedResponse response = indices.delete(new DeleteIndexRequest("wangdao1"), RequestOptions.DEFAULT);
        System.out.println("response = " + response);
    }

}