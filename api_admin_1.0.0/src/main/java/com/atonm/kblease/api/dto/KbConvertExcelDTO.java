package com.atonm.kblease.api.dto;

import com.atonm.kblease.api.common.base.BaseSearchDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

@Data
@Alias("kbConvertExcelDTO")
public class KbConvertExcelDTO {
    private String e1RowNumber;

    private String e2cmMakerName;
    private String e3cmModelName;
    private String e4cmMDetailName;
    private String e5cmGradeName;
    private String e6cmGDetailName;

    private String e7makerName;
    private String e8modelName;
    private String e9mDetailName;
    private String e10gradeName;
    private String e11gDetailName;

    private String e12kbLeaseCode;

    private String e13makerCode;
    private String e14modelCode;
    private String e15mDetailCode;
    private String e16gradeCode;
    private String e17gDetailCode;
}
