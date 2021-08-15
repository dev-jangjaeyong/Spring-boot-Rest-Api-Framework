package com.atonm.kblease.api.common.entity;

import com.atonm.kblease.api.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author jang jea young
 * @since 2018-08-09
 */
@Entity
@IdClass(UserRefreshTokenInfoId.class)
@Table(name = "USER_REFRESH_TOKEN", schema="dbo", catalog="BIG")
@Getter
@Setter
/*@EntityListeners(AuditingEntityListener.class)*/
/*@SecondaryTable(name = "USER_DEALER", schema = "dbo", catalog="BIG",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "USER_NO"))*/
public class UserRefreshTokenInfo extends BaseEntity {
    @Id
    @Column(name = "USER_NO")
    private long userNo;

    @Id
    @Column(name = "PID")
    private String pid;
   /* @EmbeddedId
    private UserRefreshTokenInfoId userRefreshTokenInfoId;

    public UserRefreshTokenInfo(UserRefreshTokenInfoId id, String userPassword, String refreshToken) {
        this.userRefreshTokenInfoId = id;
        this.userPassword = userPassword;
        this.refreshToken = refreshToken;
    }*/


    @Column(name = "USER_PASSWORD")
    private String userPassword;

    @Column(name = "REFRESH_TOKEN")
    private String refreshToken;

    @Column(name = "ACCESS_TOKEN")
    private String accessToken;

    public UserRefreshTokenInfo() {}
    public UserRefreshTokenInfo(long userNo, String pid, String userPassword, String refreshToken, String accessToken) {
        this.userNo = userNo;
        this.pid = pid;
        this.userPassword = userPassword;
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
    }
}
