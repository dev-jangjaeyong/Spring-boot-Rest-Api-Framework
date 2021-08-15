package com.atonm.kblease.api.common.base;

import com.atonm.kblease.api.commapi.dto.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.ibatis.type.Alias;

/**
 * @author jang jea young
 * @since 2018-08-09
 */
@Data
@Alias("userRefreshTokenDTO")
@EqualsAndHashCode(callSuper = true)
public class UserRefreshTokenDTO extends BaseDTO {
    private long userNo;
    private String pid;
    private String uid;
    private String userPassword;
    private String refreshToken;
    private String accessToken;
}
