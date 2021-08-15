package com.atonm.kblease.api.common.base;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import com.atonm.core.common.constant.Format;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * @author jang jea young
 * @since 2018-08-09
 */
@Data
@Alias("baseSearchDTO")
public class BaseSearchDTO {
    @NotNull(message = "{message.not-null}")
    @Size(min = 1, message = "{message.not-null}")
    private String pid;
    private String chnnlCode;
    private String keyword;
    @JsonFormat(pattern = Format.LOCAL_DATE)
    @DateTimeFormat(pattern = Format.LOCAL_DATE)
    private LocalDate startDay;
    @JsonFormat(pattern = Format.LOCAL_DATE)
    @DateTimeFormat(pattern = Format.LOCAL_DATE)
    private LocalDate endDay;
    private Long currentUserNo;
    private String currentUserId;
    private Integer currentPageNo = 1;
    private Integer rowsPerPage = 20;
    private Integer offset = (currentPageNo * rowsPerPage) - rowsPerPage;

    private Integer startPage = ((currentPageNo - 1) / rowsPerPage) * rowsPerPage + 1;
    private Integer endPage = startPage + rowsPerPage - 1;

    private Long startNumber = 0L;
    private Long endNumber = startNumber + rowsPerPage*currentPageNo;

    private String sortOrder;
    private String sortType;

    private String  getSortType () {

        try {
            if ( this.sortType.toUpperCase().equals("A") ) {
                return "ASC";
            } else if ( this.sortType.toUpperCase().equals("D") ) {
                return "DESC";
            }
        } catch (Exception e){
            return "";
        }
        return "";
    }
    
    public Integer getOffset() {
        return (currentPageNo * rowsPerPage) - rowsPerPage;
    }

    public String carSidoNo;
    public String carCityaNo;
    public String carDanjiNo;
    public String carShopNo;
    public String carShopNm;
    public String dealerNm;


    public String dealerName;
    public String carPlateNumber;



    public String   oncarDefaultSchCondiCarApplyDt;
    public String   oncarDefaultSchCondiMinSaleAmt;
    public String   oncarDefaultSchCondiMaxSaleAmt;
    public String   oncarDefaultSchCondiMinUseKm;
    public String   oncarDefaultSchCondiMaxUseKm;

    private int errcode;


}
