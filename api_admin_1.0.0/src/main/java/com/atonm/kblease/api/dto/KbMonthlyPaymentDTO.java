package com.atonm.kblease.api.dto;


import com.atonm.kblease.api.common.base.BaseSearchDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import java.util.ArrayList;

@Data
@Alias("kbMonthlyPaymentDTO")
public class KbMonthlyPaymentDTO {
    private String pid;
    private String channel;

    @ApiModelProperty(value = "차량가격")
    private int carPrice;
    @ApiModelProperty(value = "선수금 율(percent)")
    private double advanceRatio;
    @ApiModelProperty(value = "딜러수수료")
    private double dealerFee;
    @ApiModelProperty(value = "잔가군코드")
    private String residualCode;
    @ApiModelProperty(value = "리스기간")
    private int monthPeriod;
}
