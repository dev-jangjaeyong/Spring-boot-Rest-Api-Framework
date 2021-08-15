package com.atonm.kblease.api.common.base;


import com.atonm.core.common.constant.Format;
import com.atonm.kblease.api.commapi.dto.BaseDTO;
import com.atonm.kblease.api.common.enumerate.YN;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author jang jea young
 * @since 2018-08-09
 */
@Data
@Alias("userRoleHrcDTO")
public class UserRoleHrcDTO {
    private String roleName;
    private String roleHierarchy;
}
