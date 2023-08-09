package com.kyy00n.s3putobjectbysdk.s3;

import java.net.URI;
import java.net.URL;
import java.time.Duration;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Component
public class S3UrlSignAgent {

    private S3Presigner s3Presigner = S3Presigner.builder()
            .endpointOverride(URI.create("http://localhost:4566"))
            .build();
    private String bucketName = "rosie-bucket";

    public record S3PresignedUrl(String url, String httpMethod) {

    }

    public S3PresignedUrl getPresignedUrl(String key) {
        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType("image/png")
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(10))
                .putObjectRequest(objectRequest)
                .build();
        PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(presignRequest);

        // Upload content to the Amazon S3 bucket by using this URL.
        URL url = presignedRequest.url();
        return new S3PresignedUrl(url.toString(), presignedRequest.httpRequest().method().name());
    }

}
