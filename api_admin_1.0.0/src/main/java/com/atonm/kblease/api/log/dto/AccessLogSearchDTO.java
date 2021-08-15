package com.atonm.kblease.api.log.dto;

import com.atonm.kblease.api.commapi.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

@Data
@Alias("accessLogSearchDTO")
@EqualsAndHashCode(callSuper = true)
public class AccessLogSearchDTO extends BaseDTO {
    private Long accessLogNo;
    private String pid;
    private Long menuNo;
    private String chnnlCode;
    private String accessUrl;
    private String ip;
    private Long userNo;
    private String brwsrInfo;
    private String osClass;
    private String osVer;
    private String mobileYn;
    private String resultCode;
    private String errCode;
    private String ioType;

    private String searchDate;
}
