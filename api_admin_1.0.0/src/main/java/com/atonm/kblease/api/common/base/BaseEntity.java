package com.atonm.kblease.api.common.base;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author jang jea young
 * @since 2018-08-31
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
public abstract class BaseEntity implements Serializable {
    @CreatedDate
    @Column(name = "CREATE_DT", updatable = false)
    private LocalDateTime createDt;

    @CreatedBy
    @Column(name = "CREATE_UID", updatable = false)
    private BigDecimal createUserNo;

    @LastModifiedDate
    @Column(name = "UPDATE_DT")
    private LocalDateTime updateDt;

    @LastModifiedBy
    @Column(name = "UPDATE_UID")
    private BigDecimal updateUserNo;

    @PrePersist
    public void onPrePersist() {
        this.createDt = LocalDateTime.now();
        this.updateDt = this.createDt;
    }

    @PreUpdate
    public void onPreUpdate() {
        this.updateDt = LocalDateTime.now();
    }
}
