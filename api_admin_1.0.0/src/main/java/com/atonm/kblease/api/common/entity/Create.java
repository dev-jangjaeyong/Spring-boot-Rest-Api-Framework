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
public class Create {
    @Column(name = "CREATE_DT")
    private LocalDateTime createDt;

    @Column(name = "CREATE_USER_NO")
    private Long createUserNo;
}
