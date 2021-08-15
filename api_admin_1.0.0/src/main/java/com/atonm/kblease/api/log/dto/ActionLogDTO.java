package com.atonm.kblease.api.log.dto;

import com.atonm.kblease.api.commapi.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Alias("actionLogDTO")
@EqualsAndHashCode(callSuper = true)
public class ActionLogDTO extends BaseDTO {
    private Long actionLogNo;
    private String pid;
    private String channel;
    private Long apiNo;
    private String actionUrl;
    private String actionMethod;
    private String ip;
    private String userId;
    private String brwsrInfo;
    private String osClass;
    private String resultCode;
    private String resultStatus;
    private String reqArgs;
    private String resMsg;
    private LocalDateTime reqDt;
    private LocalDateTime resDt;
    private String loginSucYn;
    private BigDecimal mId;
}
