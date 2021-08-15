package com.atonm.kblease.api.dto;


import com.atonm.kblease.api.commapi.dto.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

@EqualsAndHashCode(callSuper = true)
@Data
@Alias("kbLeaseGroupDTO")
public class KbLeaseGroupDTO extends BaseDTO {
    @ApiModelProperty(value = "")
    private String groupId;
    @ApiModelProperty(value = "")
    private String groupName;
    @ApiModelProperty(value = "")
    private String dispGroupName;
    @ApiModelProperty(value = "")
    private String orgId;
    @ApiModelProperty(value = "")
    private String codeName;
    @ApiModelProperty(value = "")
    private String dispName;
    @ApiModelProperty(value = "")
    private String codeValue;
    @ApiModelProperty(value = "")
    private String sortNo;
    @ApiModelProperty(value = "")
    private String codeDesc;
    @ApiModelProperty(value = "")
    private String attr;
}
