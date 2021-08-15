package com.atonm.kblease.api.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("kbLeaseBatchDTO")
public class KbLeaseBatchDTO {
    private String year;
    private String month;

    private String yearMonth;
    private String yearFirstDay;

    private String createDt;
    private String cnt;
}
