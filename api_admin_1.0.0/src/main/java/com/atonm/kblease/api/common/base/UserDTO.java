package com.atonm.kblease.api.common.base;


import com.atonm.core.common.constant.Format;
import com.atonm.kblease.api.common.enumerate.YN;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;
import com.atonm.kblease.api.commapi.dto.BaseDTO;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author jang jea young
 * @since 2018-08-09
 */
@Data
@Alias("userDTO")
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends BaseDTO {

    private Long userNo;

    private String userTyCd;

    private String userTyCdName;

    private String userName;

    private String gender;

    private String cellPhoneNo;

    private String extnPhoneNo;

    private String zipCode;

    private String email;

    private String site;

    private String userClass;

    private String userId;

    @JsonFormat(pattern = Format.LOCAL_DATE)
    private LocalDate birthDay;

    @JsonFormat(pattern = Format.LOCAL_DATE)
    private LocalDateTime licenseStDt;

    @JsonFormat(pattern = Format.LOCAL_DATE)
    private LocalDateTime licenseEdDt;

    @JsonIgnore
    private UserDealerDTO userDealer;
}
