package cn.kiroe.mall.product.config;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Kiro
 * @Date 2024/01/18 15:21
 **/
@Configuration
public class MinioConfig {
    //  获取文件上传对应的地址
    @Value("${minio.endpointUrl}")
    public String endpointUrl;

    @Value("${minio.accessKey}")
    public String accessKey;

    @Value("${minio.secreKey}")
    public String secreKey;

    @Value("${minio.bucketName}")
    public String bucketName;

    @Bean
    public MinioClient minioClient(){

        // 使用MinIO服务的URL，端口，Access key和Secret key创建一个MinioClient对象
        MinioClient minioClient =
                MinioClient.builder()
                           .endpoint(endpointUrl)
                           .credentials(accessKey, secreKey)
                           .build();
        return minioClient;
    }
}
