package com.kyy00n.s3putobjectbysdk.controller;

import com.kyy00n.s3putobjectbysdk.s3.S3Uploader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImageController {

    private final S3Uploader s3Uploader;

    public ImageController(final S3Uploader s3Uploader) {
        this.s3Uploader = s3Uploader;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam(value = "name") String name,
                                              @RequestParam(value = "imageFile") MultipartFile image) {
        s3Uploader.uploadFile(name, image);
        return ResponseEntity.ok("success");
    }

}
