package com.kyy00n.s3putobjectbysdk.s3;

import java.io.IOException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import static org.testcontainers.containers.localstack.LocalStackContainer.Service.S3;

@Testcontainers
class S3UploaderIntegrationTest {

    private S3Uploader s3Uploader;
    private S3Client s3Client;
    DockerImageName localstackImage = DockerImageName.parse("localstack/localstack:latest-arm64");

    @Container
    public LocalStackContainer localstack = new LocalStackContainer(localstackImage)
            .withServices(S3);

    @BeforeEach
    public void setUp() {
        s3Client = S3Client.builder()
                .endpointOverride(localstack.getEndpoint())
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(localstack.getAccessKey(), localstack.getSecretKey())
                        )
                )
                .region(Region.of(localstack.getRegion()))
                .build();

        s3Uploader = new S3Uploader(s3Client);
    }

    @Test
    void 사진을_업로드하면_조회할_수_있다() throws IOException {
        //given
        s3Client.createBucket(builder -> builder.bucket("rosie-bucket"));
        String 키_이름 = "keyname";
        String 업로드_요청_파일 = "test file";
        MultipartFile multipartFile = new MockMultipartFile(키_이름, "originalname", "image/png",
                업로드_요청_파일.getBytes());

        //when
        s3Uploader.uploadFile(키_이름, multipartFile);

        //then
        String 업로드된_파일 = new String(s3Client.getObject(builder -> builder.bucket("rosie-bucket").key(키_이름))
                .readAllBytes());
        Assertions.assertThat(업로드된_파일).isEqualTo(업로드_요청_파일);
    }

}
