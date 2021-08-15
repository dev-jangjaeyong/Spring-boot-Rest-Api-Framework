package com.atonm.kblease.api.common.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import com.atonm.core.common.constant.Format;

import java.time.LocalDateTime;

/**
 * @author jang jae young
 * @since 2018-11-01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class BaseRegisterInfoDTO {
    @JsonFormat(pattern = Format.LOCAL_DATE_TIME)
    private LocalDateTime createDt;

    private Long createUserNo;

    @JsonFormat(pattern = Format.LOCAL_DATE_TIME)
    private LocalDateTime updateDt;

    private Long updateUserNo;
}
