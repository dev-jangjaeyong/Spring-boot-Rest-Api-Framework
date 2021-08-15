package com.atonm.kblease.api.dto;

import com.atonm.kblease.api.commapi.dto.BaseDTO;
import com.atonm.kblease.api.common.base.BaseSearchDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;

@Data
@Alias("KbOrgInationDTO")
public class KbOrgInationDTO {
    private BigDecimal orgId;
    private String companyName;
    private String workNo;
    private String chairman;
    private String tel;
    private String addr;
    private String createDt;
    private Long createUid;
    private String updateDt;
    private Long updateUid;
}
