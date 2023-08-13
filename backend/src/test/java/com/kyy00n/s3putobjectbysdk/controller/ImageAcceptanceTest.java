package com.kyy00n.s3putobjectbysdk.controller;

import io.restassured.response.Response;
import java.io.File;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImageAcceptanceTest {

    @Test
    void upload_via_server() {
        File imageFile = new File("/Users/oieuoa/dev/s3-put-object-by-sdk/http/narutomaki.png");

        Response response = given()
                .multiPart("imageFile", imageFile)
                .multiPart("name", "put-object-by-server.png")
                .when()
                .post("http://localhost:8080/upload");

        response.then()
                .statusCode(200)
                .body(equalTo("success"));
    }

}
