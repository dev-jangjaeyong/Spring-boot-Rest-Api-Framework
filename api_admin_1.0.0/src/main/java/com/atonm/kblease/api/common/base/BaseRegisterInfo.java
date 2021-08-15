package com.atonm.kblease.api.common.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * @author jang jae young
 * @since 2018-11-01
 */
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseRegisterInfo {
    @CreatedDate
    @Column(name = "CREATE_DT")
    private LocalDateTime createDt;

    @CreatedBy
    @Column(name = "CREATE_USER_NO")
    private Long createUserNo;

    @LastModifiedDate
    @Column(name = "UPDATE_DT")
    private LocalDateTime updateDt;

    @LastModifiedBy
    @Column(name = "UPDATE_USER_NO")
    private Long updateUserNo;
}
