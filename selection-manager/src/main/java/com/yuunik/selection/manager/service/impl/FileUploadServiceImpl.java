package com.yuunik.selection.manager.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.yuunik.selection.common.exception.YuunikException;
import com.yuunik.selection.manager.properties.MinioProperties;
import com.yuunik.selection.manager.service.FileUploadService;
import com.yuunik.selection.model.vo.common.ResultCodeEnum;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Date;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Autowired
    private MinioProperties minioProperties;

    // 文件上传
    @Override
    public String uploadFile(MultipartFile file) {
        try {
            // 创建 Minio 客户端
            MinioClient minioClient = MinioClient.builder().endpoint(minioProperties.getEndpoint())
                    .credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey())
                    .build();
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucketName()).build());
            // 判断存储桶是否存在
            if (!isExist) {
                // 不存在，创建新存储桶
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucketName()).build());
            } else {
                // 存在
                System.out.println("Bucket 'avatar-bucket' already exists");
            }
            // 获取当前日期
            String curTime = DateUtil.format(new Date(), "yyyy/MM/dd");
            // uuid
            String uuidStr = UUID.randomUUID().toString().replaceAll("-", "");
            // 获取文件名
            String fileName = file.getOriginalFilename();
            // 获取文件输入流
            InputStream fileInputStream = file.getInputStream();
            // 获取文件大小
            long fileSize = file.getSize();
            // 拼接文件名
            fileName = curTime + "/" + uuidStr + fileName;

            // 上传文件
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .bucket(minioProperties.getBucketName())
                    .stream(fileInputStream, fileSize, -1)
                    .object(fileName)
                    .build();
            minioClient.putObject(putObjectArgs);

            return minioProperties.getEndpoint() + "/" + minioProperties.getBucketName() + "/" + fileName;

        } catch (Exception e) {
            e.printStackTrace();
            // 抛出错误
            throw new YuunikException(ResultCodeEnum.FILE_UPLOAD_ERROR);
        }
    }
}
