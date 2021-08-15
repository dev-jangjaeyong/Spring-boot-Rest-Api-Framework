package com.atonm.kblease.api.dto;

import com.atonm.kblease.api.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

@EqualsAndHashCode(callSuper = true)
@Data
@Alias("kbShopInfoDTO")
public class KbShopInfoDTO extends BaseEntity {
    private String rowNumber;
    private String sido;
    private String city;
    private String danjiName;
    private String kbCapitalId;
    private String salesArea;
    private String updateDate;
    private String shopUseYn;
    private String apUseYn;

    @ApiModelProperty(value = "")
    private String shopName;

    @ApiModelProperty(value = "")
    private String name;

    @ApiModelProperty(value = "")
    private String sidoNo;

    @ApiModelProperty(value = "")
    private String cityNo;

    @ApiModelProperty(value = "")
    private String danjiNo;

    @ApiModelProperty(value = "")
    private String shopNo;

    @ApiModelProperty(value = "")
    private String chooseYn;

    @ApiModelProperty(value = "")
    private Integer apId;

    @ApiModelProperty(value = "")
    private String currentPageNo;

    @ApiModelProperty(value = "")
    private String rowsPerPage;

    @ApiModelProperty(value = "")
    private String type;
}
