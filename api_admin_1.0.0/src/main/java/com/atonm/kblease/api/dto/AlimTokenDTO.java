package com.atonm.kblease.api.dto;

import com.atonm.kblease.api.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

@Data
@Alias("alimTokenDTO")
public class AlimTokenDTO {
    private String pid;
    private String channel;

    private String alimT;
}
