package com.atonm.kblease.api.dto;

import com.atonm.kblease.api.common.base.BaseSearchDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

@EqualsAndHashCode(callSuper = true)
@Data
@Alias("kbConvertDTO")
public class KbConvertDTO extends BaseSearchDTO {
    @ApiModelProperty(value = "")
    private String convertCodeSeq;
    @ApiModelProperty(value = "")
    private String kbLeaseCode;
    @ApiModelProperty(value = "")
    private String residualCode;

    @ApiModelProperty(value = "")
    private String cmMakerName;
    @ApiModelProperty(value = "")
    private String cmModelName;
    @ApiModelProperty(value = "")
    private String cmMDetailName;
    @ApiModelProperty(value = "")
    private String cmGradeName;
    @ApiModelProperty(value = "")
    private String cmGDetailName;

    @ApiModelProperty(value = "")
    private String countryNo;
    @ApiModelProperty(value = "")
    private String makerNo;
    @ApiModelProperty(value = "")
    private String modelNo;
    @ApiModelProperty(value = "")
    private String mdetailNo;
    @ApiModelProperty(value = "")
    private String gradeNo;
    @ApiModelProperty(value = "")
    private String gdetailNo;

    @ApiModelProperty(value = "")
    private String makerName;
    @ApiModelProperty(value = "")
    private String modelName;
    @ApiModelProperty(value = "")
    private String mDetailName;
    @ApiModelProperty(value = "")
    private String gradeName;
    @ApiModelProperty(value = "")
    private String gDetailName;

    @ApiModelProperty(value = "")
    private String makerCode;
    @ApiModelProperty(value = "")
    private String modelCode;
    @ApiModelProperty(value = "")
    private String mDetailCode;
    @ApiModelProperty(value = "")
    private String gradeCode;
    @ApiModelProperty(value = "")
    private String gDetailCode;

    @ApiModelProperty(value = "리스대상", notes = "리스대상타입(radio 형식)", example = "0 = 리스비대상, 1 = 리스대상")
    private String leaseState;

    @ApiModelProperty(value = "검색어 지정 radio버튼")
    public String radioValue;
    @ApiModelProperty(value = "검색어", notes = "검색어타입(radio로 선택)", example = "dealerName=딜러명, carPlateNumber=차량번호")
    private String searchText;



}
