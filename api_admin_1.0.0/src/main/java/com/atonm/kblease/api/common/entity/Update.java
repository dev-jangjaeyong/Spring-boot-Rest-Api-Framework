package com.atonm.kblease.api.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

/**
 * @author jang jea young
 * @since 2021-05-08
 */
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Update {
    @Column(name = "UPDATE_DT")
    private LocalDateTime updateDt;

    @Column(name = "UPDATE_USER_NO")
    private Long updateUserNo;
}
