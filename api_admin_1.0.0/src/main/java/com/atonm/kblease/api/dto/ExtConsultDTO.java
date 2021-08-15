package com.atonm.kblease.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import org.apache.ibatis.type.Alias;

@Data
@Alias("extConsultDTO")
public class ExtConsultDTO {

    private String csti;
    public String carPlateNumber;
    @ApiModelProperty(value = "onCar테이블 leaseChannel 1:일반리스, 2:안심리스")
    public String leaseChannel;

    private String carMakerName;
    private String carModelDetailName;
    private String carGradeName;
    private String carGradeDetailName;

    private String carRegYear;
    private String carRegDay;
    private String carUseKm;
    @ApiModelProperty(value = "취득원가")
    private String acquistionCost;
    @ApiModelProperty(value = "원차량가격")
    private Integer originAmountSale;
    private String carAmountSale;
    private String sidoName;
    private String cityName;
    private String danjiName;
    private String shopName;

    @ApiModelProperty(value = "차량딜러 성함")
    public String dealerName;
    public String dealerHp;

    @ApiModelProperty(value = "선납금 퍼센트율")
    private String prePaymentPer;
    @ApiModelProperty(value = "선납금")
    private String prePaymentAmt;
    @ApiModelProperty(value = "잔존가")
    private String residualAmount;
    @ApiModelProperty(value = "잔존율")
    private String residualRatio;

    @ApiModelProperty(value = "리스기간")
    private String leaseMonth;
    @ApiModelProperty(value = "알선딜러 수수료")
    private String dealerFee;
    @ApiModelProperty(value = "제휴사 수수료")
    private String affiliateFee;
    @ApiModelProperty(value = "제휴사 수수료")
    private String apFee;
    @ApiModelProperty(value = "월 예상납부금액")
    private String leaseMonthCost;

    private String assistSidoName;
    private String assistCityName;
    private String assistDanjiName;
    private String assistShopName;
    @ApiModelProperty(value = "알선딜러 성함")
    private String assistDealerName;
    @ApiModelProperty(value = "알선들러 hp")
    private String assistDealerHp;
    private String customerName;
    private String customerHp;

    private String createDt;

    private String apName;
    private String apSafeHp;

}
