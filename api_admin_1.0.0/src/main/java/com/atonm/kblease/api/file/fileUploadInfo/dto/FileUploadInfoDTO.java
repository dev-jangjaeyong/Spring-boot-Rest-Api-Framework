package com.atonm.kblease.api.file.fileUploadInfo.dto;

import com.atonm.kblease.api.commapi.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author jang jea young
 * @since 2018-08-09.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FileUploadInfoDTO extends BaseDTO {
    private Long fileUploadInfoNo;
    private String serviceBoard;
    private Long boardNo;
    private String url;
    private String filePath;
    private String useYn;
    private Long sortNo;

    private List<FileUploadInfoDTO> fileUploadInfos;

    private String[] fileUrl;
}
