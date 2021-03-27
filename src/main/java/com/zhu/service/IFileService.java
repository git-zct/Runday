package com.zhu.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
    String upload(MultipartFile file, String path);
    String uploadCode(MultipartFile file, String path,Integer pathCode);
}
