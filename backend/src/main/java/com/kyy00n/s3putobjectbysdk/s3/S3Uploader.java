package com.kyy00n.s3putobjectbysdk.s3;

import java.io.IOException;
import java.net.URI;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Component
public class S3Uploader {

    private S3Client s3 = S3Client.builder()
            .endpointOverride(URI.create("http://localhost:4566"))
            .forcePathStyle(true)
            .build();

    private String bucketName = "rosie-bucket";

    public void uploadFile(String key, MultipartFile file) {
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType("image/png")
                .build();

        s3.putObject(objectRequest, RequestBody.fromBytes(getBytes(file)));
    }

    private byte[] getBytes(final MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new IllegalArgumentException("file.getBytes() error");
        }
    }


}
