package com.atonm.kblease.api.dto;


import com.atonm.kblease.api.commapi.dto.BaseDTO;
import com.atonm.kblease.api.common.base.BaseSearchDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

@EqualsAndHashCode(callSuper = true)
@Data
@Alias("kbLeaseCodeDTO")
public class KBLeaseCodeDTO extends BaseSearchDTO {
    @ApiModelProperty(value = "useYn변경유무 기본값 N")
    private String changeUseYn;
    @ApiModelProperty(value = "")
    private String codeID;
    @ApiModelProperty(value = "")
    private String residual12Rate;
    @ApiModelProperty(value = "")
    private String residual24Rate;
    @ApiModelProperty(value = "")
    private String residual36Rate;
    @ApiModelProperty(value = "")
    private String residual48Rate;
    @ApiModelProperty(value = "")
    private String residual60Rate;
    @ApiModelProperty(value = "")
    private String useYN;
    @ApiModelProperty(value = "")
    private String leaseChannel;
}
