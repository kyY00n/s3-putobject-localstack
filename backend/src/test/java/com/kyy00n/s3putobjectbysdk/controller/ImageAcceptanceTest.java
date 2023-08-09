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
    void presigned_url_얻기() {
        Response response = given()
                .when()
                .get("http://localhost:8080/presigned-url?name=presigned-url-test");

        response.then()
                .statusCode(200);
    }

    @Test
    void presigned_url로_바로_업로드() {
        Response response1 = given()
                .when()
                .get("http://localhost:8080/presigned-url?name=presigned-url-test");

        response1.then()
                .statusCode(200);

        String url = response1.jsonPath().getString("url");

        File imageFile = new File("/Users/oieuoa/dev/s3-put-object-by-sdk/http/narutomaki.png");

        Response response2 = given()
                .multiPart("name", "narutomaki.png")
                .multiPart("imageFile", imageFile)
                .when()
                .put(url);

        response2.then()
                .statusCode(200);
    }

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
