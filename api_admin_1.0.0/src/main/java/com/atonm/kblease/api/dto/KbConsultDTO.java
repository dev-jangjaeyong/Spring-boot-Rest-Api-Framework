package com.atonm.kblease.api.dto;

import com.atonm.kblease.api.common.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@EqualsAndHashCode(callSuper = true)
@Data
@Alias("kbConsultDTO")
public class KbConsultDTO extends BaseEntity {
    private String rowNumber;

    @ApiModelProperty(value = "TA_CONSILTING - PK KEY")
    private String consultId;

    @ApiModelProperty(value = "")
    private String sidoName;

    @ApiModelProperty(value = "")
    private String cityName;
    @ApiModelProperty(value = "")
    private String danjiName;

    @ApiModelProperty(value = "")
    private String shopName;

    @ApiModelProperty(value = "")
    private String carOwnerName;

    @ApiModelProperty(value = "")
    private String carOwnerHp;

    @ApiModelProperty(value = "")
    private String carPlateNumber;

    @ApiModelProperty(value = "")
    private String carFrameNo;

    @ApiModelProperty(value = "알선딜러 시도")
    private String assistSidoName;

    @ApiModelProperty(value = "알선딜러 지역")
    private String assistCityName;

    @ApiModelProperty(value = "알선딜러 단지")
    private String assistDanjiName;

    @ApiModelProperty(value = "알선딜러 상사")
    private String assistShopName;


    @ApiModelProperty(value = "알선딜러 성함")
    private String assistDealerName;

    @ApiModelProperty(value = "알선딜러 연락처")
    private String assistDealerHp;

    @ApiModelProperty(value = "고객 성함")
    private String customerName;

    @ApiModelProperty(value = "고객 연락처")
    private String customerHp;

    @ApiModelProperty(value = "AP 성함")
    private String apName;

    @ApiModelProperty(value = "AP 연락처")
    private String apHp;

    @ApiModelProperty(value = "AP확인 날짜")
    private String apCheckTime;

    private String createFDt;
    private String createTDt;

}
