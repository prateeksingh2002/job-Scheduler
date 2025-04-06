package com.jobscheduler1.service;

import io.minio.*;
import io.minio.errors.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BinaryStorageService {
    private final MinioClient minioClient;

    @Value("${minio.bucket-name}")
    private String bucketName;

    public String uploadBinary(MultipartFile file) {
        try {
            String objectName = UUID.randomUUID().toString();

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());

            return objectName;
        } catch (Exception e) {
            throw new RuntimeException("Failed to upload binary", e);
        }
    }

    public InputStream downloadBinary(String objectName) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build());
        } catch (Exception e) {
            throw new RuntimeException("Failed to download binary", e);
        }
    }

    public void deleteBinary(String objectName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .build());
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete binary", e);
        }
    }
}