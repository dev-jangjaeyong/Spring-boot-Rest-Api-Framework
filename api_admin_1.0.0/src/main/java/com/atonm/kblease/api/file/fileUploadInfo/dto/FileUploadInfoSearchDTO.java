package com.atonm.kblease.api.file.fileUploadInfo.dto;

import com.atonm.kblease.api.common.base.BaseSearchDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jang jea young
 * @since 2018-08-09.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FileUploadInfoSearchDTO extends BaseSearchDTO {
    /**
     * 파일업로드번호
     */
    private Long fileUploadInfoNo;

    /**
     * 게시판번호
     */
    private Long boardNo;

    /**
     * 게시판번호
     */
    private String boardTyCd;

    private String serviceBoard;
}
