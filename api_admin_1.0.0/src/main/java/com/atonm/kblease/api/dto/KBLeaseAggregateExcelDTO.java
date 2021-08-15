package com.atonm.kblease.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("kBLeaseAggregateExcelDTO")
public class KBLeaseAggregateExcelDTO {
    private String e1RowNumber;

    private String el2carPlateNumber;
    private String el3createDt;
    private String el4channelType;
    private String el5connectUserId;
    private String el6channelType;

}
