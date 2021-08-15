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
@Alias("kbConsultSearchDTO")
public class KbConsultSearchDTO extends BaseSearchDTO {
    @ApiModelProperty(value = "요청 시작일")
    private String tbxFromDate;

    @ApiModelProperty(value = "요청 종료일")
    private String tbxToDate;

    @ApiModelProperty(value = "")
    private String makerNo;

    @ApiModelProperty(value = "")
    private String modelNo;

    @ApiModelProperty(value = "")
    private String mdetailNo;

    @ApiModelProperty(value = "AP 아이디")
    private String apId;

    @ApiModelProperty(value = "AP 아이디")
    private String customerName;

    @ApiModelProperty(value = "AP 아이디")
    private String apCheck;

    @ApiModelProperty(value = "차대번호")
    private String carFrameNo;

    @ApiModelProperty(value = "알선딜러 성함")
    private String assistDealerName;
}
