package com.atonm.kblease.api.common.base;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author jang jea young
 * @since 2018-09-12
 */
@Entity
@Table(name = "USER_DEALER", schema="dbo", catalog="BIG")
@Getter
@Setter
public class UserDealer extends BaseEntity {
    /**
     * 회원번호
     */
    @Id
    @Column(name = "USER_NO")
    private Long userNo;
}
