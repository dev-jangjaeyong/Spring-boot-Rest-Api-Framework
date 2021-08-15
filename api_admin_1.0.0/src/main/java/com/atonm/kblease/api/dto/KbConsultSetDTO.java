package com.atonm.kblease.api.dto;

import com.atonm.core.common.constant.Constant;
import com.atonm.kblease.api.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@EqualsAndHashCode(callSuper = true)
@Data
@Alias("kbConsultSetDTO")
public class KbConsultSetDTO extends BaseEntity {
    @ApiModelProperty(value = "PK")
    private BigInteger consultId;

    @ApiModelProperty(value = "PID")
    private String pid;

    @ApiModelProperty(value = "CHANNEL")
    private String channel;

    @NotNull(message = "필수 값이 없습니다.오류가 계속될 시 고객센터로 연락 바랍니다.")
    @ApiModelProperty(value = "")
    private String apId;

    @NotNull(message = "필수 값이 없습니다.오류가 계속될 시 고객센터로 연락 바랍니다.")
    @ApiModelProperty(value = "")
    private String carNo;

    @NotNull(message = "필수 값이 없습니다.오류가 계속될 시 고객센터로 연락 바랍니다.")
    @ApiModelProperty(value = "리스기간")
    private String leaseMonth;

    @NotNull(message = "필수 값이 없습니다.오류가 계속될 시 고객센터로 연락 바랍니다.")
    @ApiModelProperty(value = "월 예상납부금액")
    private String leaseMonthCost;

    @NotNull(message = "필수 값이 없습니다.오류가 계속될 시 고객센터로 연락 바랍니다.")
    @ApiModelProperty(value = "취득원가")
    private String acquistionCost;

    @NotNull(message = "필수 값이 없습니다.오류가 계속될 시 고객센터로 연락 바랍니다.")
    @ApiModelProperty(value = "잔존가치")
    private String residualAmount;

    @NotNull(message = "필수 값이 없습니다.오류가 계속될 시 고객센터로 연락 바랍니다.")
    @ApiModelProperty(value = "선납금 퍼센트율")
    private String prePaymentPer;

    @NotNull(message = "필수 값이 없습니다.오류가 계속될 시 고객센터로 연락 바랍니다.")
    @ApiModelProperty(value = "선납금")
    private String prePaymentAmt;

    @NotNull(message = "필수 값이 없습니다.오류가 계속될 시 고객센터로 연락 바랍니다.")
    @ApiModelProperty(value = "신청자 userNo")
    private String customerUserNo;

    @NotNull(message = "필수 값이 없습니다.오류가 계속될 시 고객센터로 연락 바랍니다.")
    @ApiModelProperty(value = "신청자 휴대폰")
    private String customerHp;

    @NotNull(message = "필수 값이 없습니다.오류가 계속될 시 고객센터로 연락 바랍니다.")
    @ApiModelProperty(value = "신청자 성함")
    private String customerName;

    @NotNull(message = "필수 값이 없습니다.오류가 계속될 시 고객센터로 연락 바랍니다.")
    @ApiModelProperty(value = "알선딜러 수수료")
    private String dealerFee;
    @NotNull(message = "필수 값이 없습니다.오류가 계속될 시 고객센터로 연락 바랍니다.")

    @ApiModelProperty(value = "KB설정 딜러수수료")
    private String kbOfferDealerFee;

}
