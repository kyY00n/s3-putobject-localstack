package com.kyy00n.s3putobjectbysdk.s3;

import java.io.IOException;
import java.net.URI;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;

public class S3UploaderIntegrationTestWithLocal {

    private S3Uploader s3Uploader;
    private S3Client s3Client;

    @BeforeEach
    public void setUp() {
        s3Client = S3Client.builder()
                .endpointOverride(URI.create("http://localhost:4566"))
                .forcePathStyle(true)
                .build();

        s3Uploader = new S3Uploader(s3Client);
    }

    @Test
    void 사진을_업로드하면_조회할_수_있다() throws IOException {
        //given
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
