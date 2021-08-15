package com.atonm.kblease.api.dto;

import com.atonm.kblease.api.common.base.BaseEntity;
import com.atonm.kblease.api.common.base.BaseSearchDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@EqualsAndHashCode(callSuper = true)
@Data
@Alias("KbConsultStaticsDTO")
public class KbConsultStaticsDTO extends BaseSearchDTO{
    @ApiModelProperty(value = "요청 시작일")
    private String tbxFromDate;

    @ApiModelProperty(value = "요청 종료일")
    private String tbxToDate;

    @ApiModelProperty(value = "")
    private int dateType;

    private String createDt;
    private String totalCount;
    private String confirmCount;
}
