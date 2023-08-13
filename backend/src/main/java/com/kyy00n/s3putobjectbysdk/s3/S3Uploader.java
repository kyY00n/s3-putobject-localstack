package com.kyy00n.s3putobjectbysdk.s3;

import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
public class S3Uploader {

    private final S3Client s3Client;

    public S3Uploader(final S3Client s3Client) {
        this.s3Client = s3Client;
    }

    private String bucketName = "rosie-bucket";

    public void uploadFile(String key, MultipartFile file) {
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType("image/png")
                .build();

        s3Client.putObject(objectRequest, RequestBody.fromBytes(getBytes(file)));
    }

    private byte[] getBytes(final MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new IllegalArgumentException("file.getBytes() error");
        }
    }


}
