package com.atonm.kblease.api.dto;


import com.atonm.kblease.api.commapi.dto.BaseDTO;
import com.atonm.kblease.api.common.base.BaseSearchDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import java.util.ArrayList;

@EqualsAndHashCode(callSuper = true)
@Data
@Alias("kbTermDTO")
public class KbTermDTO extends BaseSearchDTO {

    private ArrayList<KbTermDTO> editTermList;

    @ApiModelProperty(value = "")
    private String groupID;
    @ApiModelProperty(value = "")
    private String sortNo;
    @ApiModelProperty(value = "")
    private String codeValue;
    private String userNo;
    private String userOrgId;
}