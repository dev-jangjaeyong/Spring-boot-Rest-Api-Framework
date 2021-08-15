package com.atonm.kblease.api.common.base;

import com.atonm.kblease.api.commapi.dto.BaseDTO;
import com.atonm.kblease.api.common.entity.UserRefreshTokenInfoId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author jang jea young
 * @since 2018-08-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Alias("userRefreshTokenInfoDTO")
public class UserRefreshTokenInfoDTO extends BaseDTO {
    public UserRefreshTokenInfoDTO(){};
    public UserRefreshTokenInfoDTO(BigDecimal mId, String sId, String pid, String refreshToken) {
        this.mId = mId;
        this.sId = sId;
        this.refreshToken = refreshToken;
        this.setPid(pid);
    }

    private BigDecimal mId;
    private String sId;
    private String refreshToken;
    private String mToken;
}
