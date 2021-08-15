package com.atonm.kblease.api.log.dto;

import com.atonm.kblease.api.commapi.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Data
@Alias("accessLogDTO")
@EqualsAndHashCode(callSuper = true)
public class AccessLogDTO extends BaseDTO {
    private Long accessLogNo;
    private String pid;
    private String channel;
    private Long menuNo;
    private String accessUrl;
    private String ip;
    private String userId;
    private String brwsrInfo;
    private String osClass;
    private String resultCode;
    private String resultStatus;
    private String reqArgs;
}
