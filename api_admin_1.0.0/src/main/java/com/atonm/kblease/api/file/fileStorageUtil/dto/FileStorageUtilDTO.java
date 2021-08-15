package com.atonm.kblease.api.file.fileStorageUtil.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author jang jea young
 * @since 2018-08-09.
 */
@Data
public class FileStorageUtilDTO {
    private String boardNo;
    private String seq;
    private List<String> fileNames;

    private MultipartFile[] files;
    private String serviceBoard;

    private String imagePath;
    private String imageUrl;

    private Long createUserNo;
}
