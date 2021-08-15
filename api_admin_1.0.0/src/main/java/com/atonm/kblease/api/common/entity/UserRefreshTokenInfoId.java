package com.atonm.kblease.api.common.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author jang jea young
 * @since 2021-05-20
 */
//@Embeddable
@Data
public class UserRefreshTokenInfoId implements Serializable {
    private static final long serialVersionUID = 1L;

    //@Column(name = "USER_ID")
    private long userNo;

    //@Column(name = "PID")
    private String pid;

    public UserRefreshTokenInfoId() {}
    public UserRefreshTokenInfoId(long userNo, String pid) {
        this.userNo = userNo;
        this.pid = pid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRefreshTokenInfoId userRefreshTokenInfoId = (UserRefreshTokenInfoId) o;
        return userNo == userRefreshTokenInfoId.userNo &&
                pid.equalsIgnoreCase(userRefreshTokenInfoId.pid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userNo, pid);
    }
}
