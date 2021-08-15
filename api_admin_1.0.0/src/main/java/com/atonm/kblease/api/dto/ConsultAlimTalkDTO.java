package com.atonm.kblease.api.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.HashMap;

@Data
@Alias("consultAlimTalkDTO")
public class ConsultAlimTalkDTO {
    private String pid;
    private String channel;

    @ApiModelProperty(value = "push 서버 싱글톤에 적재된 데이터불러올 코드")
    private String sgtCode;

    @ApiModelProperty(value = "가져올 템플릿코드")
    private String tplCode;
    @ApiModelProperty(value = "딜러에게 보낼 템플릿코드")
    private String dealerTplCode;
    @ApiModelProperty(value = "실패시 발송여부")
    private String failOver;


    @ApiModelProperty(value = "받으실분 전화번호 ex)01027471389")
    private String apHp;
    private String apName;
    private String apSafeHp;
    @ApiModelProperty(value = "알선딜러 성함")
    private String assistDealerName;
    @ApiModelProperty(value = "알선딜러 연락처")
    private String assistDealerHp;
    @ApiModelProperty(value = "고객 성함")
    private String customerName;
    @ApiModelProperty(value = "고객 연락처")
    private String customerHp;

    private String csti;
    @ApiModelProperty(value = "암호화된 csti 토큰값")
    private String enToken;

    public String carPlateNumber;
    private String carMakerName;
    private String carModelDetailName;
    private String carGradeName;
    private String carGradeDetailName;

    private String carRegYear;
    private String carRegDay;
    private String carUseKm;
    private String carAmountSale ;
    @ApiModelProperty(value = "취득원가")
    private String acquistionCost ;

    private String sidoName;
    private String cityName;
    private String danjiName;
    private String shopName;

    private String sendState;
}
