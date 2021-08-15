package com.atonm.kblease.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import org.apache.ibatis.type.Alias;

@Data
@Alias("extReqDTO")
public class ExtReqDTO {
    private String pid;
    private String channel;

    @ApiModelProperty(value = "consultId")
    private String csti;
}
