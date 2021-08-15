package com.atonm.kblease.api.common.entity;


import com.atonm.kblease.api.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author jang jea young
 * @since 2021-05-08
 */
@Entity
@Table(name = "ONCAR")
@Getter
@Setter
public class Oncar extends BaseEntity {
    /**
     * 차량정보번호
     */
    @Id
    @Column(name = "CAR_NO")
    private String carNo;
}
