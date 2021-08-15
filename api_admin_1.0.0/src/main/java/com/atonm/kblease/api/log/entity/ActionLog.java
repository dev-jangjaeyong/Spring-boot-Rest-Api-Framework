package com.atonm.kblease.api.log.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ACTION_LOG")
@Getter
@Setter
public class ActionLog {
    /**
     * API ID
     */
    @Id
    @Column(name = "ACTION_LOG_NO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "code_sq_generator")
    @SequenceGenerator(name="code_sq_generator", sequenceName = "SQ_ACTION_LOG", allocationSize=1)
    private Long actionLogNo;
    /**
     * PID
     */
    @Column(name = "PID")
    private String pid;

    @Column(name = "API_NO")
    private Long apiNo;

    @Column(name = "CHNNL_CODE")
    private String chnnlCode;

    @Column(name = "ACTION_URL")
    private String actionUrl;

    @Column(name = "IP")
    private String ip;

    @Column(name = "USER_NO")
    private Long userNo;

    @Column(name = "BRWSR_INFO")
    private String brwsrInfo;

    @Column(name = "OS_CLASS")
    private String osClass;

    @Column(name = "OS_VER")
    private String osVer;

    @Column(name = "MOBILE_YN")
    private String mobileYn;

    @Column(name = "RESULT_CODE")
    private String resultCode;

    @Column(name = "ERR_CODE")
    private String errCode;

    @Column(name = "IO_TYPE")
    private String ioType;

    @Column(name = "REQ_DT")
    private LocalDateTime reqDt;

    @Column(name = "RES_DT")
    private LocalDateTime resDt;

    @CreatedDate
    @Column(name = "CREATE_DT", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd kk:mm:ss")
    private LocalDateTime createDt = LocalDateTime.now();

    @Column(name = "ACTION_METHOD")
    private String actionMethod;
}
