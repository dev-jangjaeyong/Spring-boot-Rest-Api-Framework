package com.atonm.kblease.api.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("kBConsultExcelDTO")
public class KBConsultExcelDTO {
    private String e1RowNumber;

    private String e2Sido;
    private String e3City;
    private String e4Danji;
    private String e5Shop;
    private String e6Dealer;
    private String e7DealerHp;

    private String e8CarPlateNumber;
    private String e9FrameNo;

    private String e10AssistSido;
    private String e11AssistCity;
    private String e12AssistDanji;
    private String e13AssistShop;
    private String e14AssistName;
    private String e15AssistHp;

    private String e16CustomerName;
    private String e17CustomerHp;

    private String e18ApName;
    private String e19ApHp;
    private String e20CreateDt;
    private String e21ApCheckTime;
}
