package com.atonm.kblease.api.file.fileUploadInfo.entity;

import com.atonm.kblease.api.common.base.BaseEntity;
import com.atonm.kblease.api.common.enumerate.YN;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author jang jea young
 * @since 2018-08-09.
 */
@Entity
@Table(name = "FILE_UPLOAD_INFO")
@Getter
@Setter
public class FileUploadInfo extends BaseEntity {
    /**
     * 파일업로드번호
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "code_sq_generator")
    @SequenceGenerator(name="code_sq_generator", sequenceName = "sq_file_upload_info", allocationSize=1)
    @Column(name = "FILE_UPLOAD_INFO_NO")
    private Long fileUploadInfoNo;

    /**
     * 게시판종류(문의게시판,공지사항,게시글
     */
    @Column(name = "SERVICE_BOARD")
    private String serviceBoard;

    /**
     * 게시판번호
     */
    @Column(name = "BOARD_NO")
    private Long boardNo;

    /**
     * URL주소
     */
    @Column(name = "URL")
    private String url;

    /**
     * 저장위치
     */
    @Column(name = "FILE_PATH")
    private String filePath;

    /**
     * 사용여부
     */
    @Column(name = "USE_YN")
    private YN useYn;

    /**
     * 정렬순번
     */
    @Column(name = "SORT_NO")
    private Long sortNo;
}
