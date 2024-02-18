package cn.kiroe.demo.es;

import cn.kiroe.demo.BaseTest;
import com.github.xiaoymin.knife4j.annotations.Ignore;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

/**
 * @Author Kiro
 * @Date 2024/01/25 14:29
 **/
public class DocTest extends BaseTest {

    // 添加文档
      void testAddDoc() throws IOException {
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.index("user");
        // indexRequest.id(); 指定id
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "lll");
        map.put("age", 40);
        map.put("id", 123);
        indexRequest.source(map);
        // 版本过低，返回的报文和之前的不同
        IndexResponse response = client.index(indexRequest, RequestOptions.DEFAULT);
        String index = indexRequest.index();
        String type = response.getType();
        String id = response.getId();
        DocWriteResponse.Result result = response.getResult();
    }

    // 修改文档 -- 直接覆盖,全量修改

      void testUpdateDocAll() throws IOException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "lll");
        map.put("age", 40);
        map.put("id", 123);
        IndexResponse index = client.index(new IndexRequest("user").source(map), RequestOptions.DEFAULT);
    }

    // 修改部分
      @Ignore
    void testUpdateDocPart() throws IOException {
        // 调用api发起 请求
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.index("user");
        updateRequest.id("3"); // 设置修改的id
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", 123);
        updateRequest.doc(map);
        client.update(updateRequest, RequestOptions.DEFAULT);
    }

    // 查询文档
    // GET /user/_doc/1
      @Ignore
    public void testQueryDocById() throws IOException {
        GetRequest getRequest = new GetRequest();
        getRequest.index("user");
        getRequest.id("3");
        GetResponse documentFields = client.get(getRequest, RequestOptions.DEFAULT);
    }

    // 删除文档
      @Ignore
    public void testDeleteDocById() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest();
        deleteRequest.index("user");
        deleteRequest.id("3");
        DeleteResponse delete = client.delete(deleteRequest, RequestOptions.DEFAULT);
    }
}
