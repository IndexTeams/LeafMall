package cn.kiroe.demo.es;

import cn.kiroe.demo.BaseTest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @Author Kiro
 * @Date 2024/01/25 15:33
 **/
public class BulkTest extends BaseTest {

      void testBulk() throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        // index doc操作
        bulkRequest.add(new IndexRequest());
        // 删除操作
        bulkRequest.add(new DeleteRequest());
        // 修改文档的请求
        bulkRequest.add(new UpdateRequest());
        // 查询的无法进行批量查询
        BulkResponse bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
    }
}
