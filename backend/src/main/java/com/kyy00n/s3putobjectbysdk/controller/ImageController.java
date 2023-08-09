package com.kyy00n.s3putobjectbysdk.controller;

import com.kyy00n.s3putobjectbysdk.s3.S3Uploader;
import com.kyy00n.s3putobjectbysdk.s3.S3UrlSignAgent;
import com.kyy00n.s3putobjectbysdk.s3.S3UrlSignAgent.S3PresignedUrl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageController {

    private final S3Uploader s3Uploader;

    private final S3UrlSignAgent s3UrlSignAgent;

    public ImageController(final S3Uploader s3Uploader, final S3UrlSignAgent s3UrlSignAgent) {
        this.s3Uploader = s3Uploader;
        this.s3UrlSignAgent = s3UrlSignAgent;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam(value = "name") String name,
                                              @RequestParam(value = "imageFile") MultipartFile image) {
        s3Uploader.uploadFile(name, image);
        return ResponseEntity.ok("success");
    }

    @GetMapping("/presigned-url")
    public ResponseEntity<S3PresignedUrl> getPresignedUrl(@RequestParam(value = "name") String name) {
        return ResponseEntity.ok(s3UrlSignAgent.getPresignedUrl(name));
    }

}
