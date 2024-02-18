package cn.kiroe.demo;

import io.minio.BucketExistsArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.UploadObjectArgs;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @Author Kiro
 * @Date 2024/01/18 14:42
 **/
@SpringBootTest
@Slf4j
public class MinioTest {

    void minioTest() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        log.info("test");
        MinioClient minioClient = MinioClient.builder().endpoint("http://jfl.kiroe.cn:9002")
                                             .credentials("minioadmin", "minioadmin")
                                             .build();
        // 2. 创建桶
        // minioClient.makeBucket(MakeBucketArgs
        //         .builder().bucket("55th") // 设置桶的名称
        //         .build());


        // 3. 判断同是否存在
        boolean b = minioClient.bucketExists(BucketExistsArgs.builder().bucket("55th")
                                                             .build());
        log.info(String.valueOf(b));

        // 4. 上传文件
        ObjectWriteResponse objectWriteResponse = minioClient.uploadObject(UploadObjectArgs.builder()
                                                                                           .bucket("55th")
                                                                                           .object("common.yaml")
                                                                                           .filename("/Users/kiro/Downloads/DEFAULT_GROUP/common.yaml")
                                                                                           .build());
        log.info(objectWriteResponse.toString());
    }

}
