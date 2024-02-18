package cn.kiroe.mall.product.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import cn.kiroe.mall.common.result.Result;
import io.minio.*;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author Kiro
 * @Date 2024/01/18 15:00
 **/
@Slf4j
@RestController
@RequiredArgsConstructor
public class FileUploadController {

    private final MinioClient minioClient;
    @Value("${minio.bucketName}")
    public String bucketName;
    @Value("${minio.endpointUrl}")
    public String endpointUrl;

    //  文件上传控制器
    @PostMapping("/admin/product/fileUpload")
    public Result fileUpload(@NotNull @RequestParam("file") MultipartFile file) throws Exception {
        //  准备获取到上传的文件路径！
        String url = "";

        // 检查存储桶是否已经存在
        boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (isExist) {
            System.out.println("Bucket already exists.");
        } else {
            // 创建一个名为asiatrip的存储桶，用于存储照片的zip文件。
            minioClient.makeBucket(MakeBucketArgs.builder()
                                                 .bucket(bucketName)
                                                 .build());
        }
        // 获取文件后缀
        String originalFilename = file.getOriginalFilename();
        String suffix = FileUtil.getSuffix(originalFilename);
        //  定义一个文件的名称 : 文件上传的时候，名称不能重复！
        String fileName = CharSequenceUtil.format("{}-{}.{}"
                , System.currentTimeMillis(), IdUtil.randomUUID(), suffix);

        // 使用putObject上传一个文件到存储桶中。
        ObjectWriteResponse objectWriteResponse = minioClient.putObject(
                PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(
                                     file.getInputStream(), file.getSize(), -1)
                             .contentType(file.getContentType())
                             .build());
        if (objectWriteResponse == null) {
            return Result.fail("文件上传失败");
        }
        url = CharSequenceUtil.format("{}/{}/{}", endpointUrl, bucketName, fileName);

        log.info("url: " + url);
        //  将文件上传之后的路径返回给页面！
        return Result.ok(url);
    }

}
