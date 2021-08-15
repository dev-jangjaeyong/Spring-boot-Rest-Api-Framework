package com.atonm.kblease.api.common.base;

import com.atonm.kblease.api.utils.SecurityUtils;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

/**
 * @author jang jea young
 * @since 2018-08-31
 */
public class EntityListener {
    @PrePersist
    public void prePersist(Object object) {
        if (object instanceof BaseEntity) {
            ((BaseEntity) object).setCreateDt(LocalDateTime.now());
            ((BaseEntity) object).setCreateUserNo(SecurityUtils.currentUserNo());
        }
    }

    @PreUpdate
    public void preUpdate(Object object) {
        if (object instanceof BaseEntity) {
            ((BaseEntity) object).setUpdateDt(LocalDateTime.now());
        }
    }
}
