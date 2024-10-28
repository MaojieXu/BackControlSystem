package com.maojie.controller;

import com.maojie.pojo.Result;
import com.maojie.utils.AliOssUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {

    /**
     * @Description: Handles file upload to AliCloud OSS. Generates a unique filename for the file, uploads it, and returns the file's URL.
     * @Param: file - MultipartFile representing the file to be uploaded
     * @return: Result<String> - Success message containing the URL of the uploaded file
     * @Author: Maojie Xu
     * @Date: 10/25/2024
     */
    @PostMapping("upload")
    public Result<String> upload(MultipartFile file) throws Exception {
        String originalFilename = file.getOriginalFilename();
        String filename = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));
        String url = AliOssUtil.uploadFile(filename, file.getInputStream());
        return Result.success(url);
    }
}
