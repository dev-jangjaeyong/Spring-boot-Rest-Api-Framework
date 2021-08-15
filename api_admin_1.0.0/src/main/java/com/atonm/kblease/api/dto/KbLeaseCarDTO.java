package com.atonm.kblease.api.dto;

import com.atonm.kblease.api.common.base.BaseSearchDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import java.util.ArrayList;

@Data
@EqualsAndHashCode(callSuper = true)
@Alias("kbLeaseCarDTO")
public class KbLeaseCarDTO extends BaseSearchDTO {
    private String uid;
    @ApiModelProperty(value = "")
    private ArrayList<Integer> carNos;
    @ApiModelProperty(value = "")
    private String rowNumber;
    @ApiModelProperty(value = "")
    private String carNo;
    @ApiModelProperty(value = "")
    private String leaseState;
    @ApiModelProperty(value = "")
    private String updateDt;
    @ApiModelProperty(value = "")
    private String carPlateNumber;
    @ApiModelProperty(value = "")
    private String carMakerName;
    @ApiModelProperty(value = "")
    private String carModelName;
    @ApiModelProperty(value = "")
    private String carModelDetailName;
    @ApiModelProperty(value = "")
    private String carGradeName;
    @ApiModelProperty(value = "")
    private String carGradeDetailName;
    @ApiModelProperty(value = "")
    private String carRegYear;
    @ApiModelProperty(value = "")
    private String carRegDay;
    @ApiModelProperty(value = "")
    private String carFuel;
    @ApiModelProperty(value = "")
    private String carUseKm;
    @ApiModelProperty(value = "")
    private String carAmountSale;
    @ApiModelProperty(value = "")
    private String sidoName;
    @ApiModelProperty(value = "")
    private String cityName;
    @ApiModelProperty(value = "")
    private String danjiName;
    @ApiModelProperty(value = "")
    private String shopName;
    @ApiModelProperty(value = "")
    private String userName;
    @ApiModelProperty(value = "")
    private String userHp;
    @ApiModelProperty(value = "")
    private String lastUpdateDate;
}
