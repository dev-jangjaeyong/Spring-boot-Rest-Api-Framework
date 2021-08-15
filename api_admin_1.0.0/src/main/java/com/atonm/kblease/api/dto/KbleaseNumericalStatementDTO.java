package com.atonm.kblease.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("kbleaseNumericalStatementDTO")
public class KbleaseNumericalStatementDTO {
    private String year;
    private String month;

    @ApiModelProperty(value = "검색용 조건1")
    private String yearMonth;
    @ApiModelProperty(value = "검색용 조건2")
    private String yearFirstDay;


    private String createDt;
    @ApiModelProperty(value = "페이지 호출 웹")
    private String useType1Web;
    @ApiModelProperty(value = "페이지 호출 모바일")
    private String useType1Mo;

    @ApiModelProperty(value = "리스계산 웹")
    private String useType2Web;
    @ApiModelProperty(value = "리스계산 모바일")
    private String useType2Mo;

    @ApiModelProperty(value = "상담요청 웹")
    private String useType3Web;
    @ApiModelProperty(value = "상담요청 모바일")
    private String useType3Mo;

    @ApiModelProperty(value = "리스가이드 웹")
    private String useType4Web;
    @ApiModelProperty(value = "리스가이드 모바일")
    private String useType4Mo;
}
