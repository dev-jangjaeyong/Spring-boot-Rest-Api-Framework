package com.atonm.kblease.api.dto;

import com.atonm.kblease.api.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

@Data
@Alias("kbLeaseAggreGateDTO")
public class KbLeaseAggreGateDTO {
    @ApiModelProperty(value = "집계 시작일")
    private String tbxFromDate;

    @ApiModelProperty(value = "집계 종료일")
    private String tbxToDate;

    private String createDt;

    @ApiModelProperty(value = "집계 이용 타입 1:페이지호출 2:계산기호출 3:상담요청 4:가이드(READ)")
    private String useType;

    public String carPlateNumber;

    public String connectUserId;

    public String channelType;

    @ApiModelProperty(value = "")
    private String currentPageNo;

    @ApiModelProperty(value = "")
    private String rowsPerPage;
}
