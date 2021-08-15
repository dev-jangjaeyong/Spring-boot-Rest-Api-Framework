package com.atonm.kblease.api.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.ArrayList;

@Data
@Alias("kbCalculatorDTO")
public class KbCalculatorDTO {

    private ArrayList<KbCalculatorDTO> editCalcList;

    private String setupCode;
    private String groupId;
    private String codeValue;

    private String userNo;
    private String userOrgId;
    private String targetKey;

    private String irr12Month;
    private String irr24Month;
    private String irr36Month;
    private String irr48Month;
    private String irr60Month;

    private String feeAffiliate;
    private String feeAp;
    private String feeDealer;



}
