package com.atonm.kblease.api.commapi.dto;


import com.atonm.kblease.api.common.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author jang jea young
 * @since 2018-08-08
 */
@Entity
@Table(name = "COMM_CODE")
@Getter
@Setter
public class CommonCode extends BaseEntity {
    /**
     * 코드
     */
    @Id
    @Column(name ="CODE_NO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "code_sq_generator")
    @SequenceGenerator(name="code_sq_generator", sequenceName = "sq_comm_code", allocationSize=1)
    private Long codeNo;
    /**
     * 상위코드
     */
    @Column(name = "UP_CODE_NO")
    private Long upCodeNo;
    /**
     * 코드
     */
    @Column(name = "CODE")
    private String code;
    /**
     * 코드명
     */
    @Column(name = "CODE_NAME")
    private String codeName;
    /**
     * 정렬번호
     */
    @Column(name = "SORT_NO")
    private Long sortNo;
    /**
     * 사용여부
     */
    @Column(name = "USE_YN")
    private String useYn;
    /**
     * 설명
     */
    @Column(name = "DSCRP")
    private String dscrp;
    /**
     * PID
     */
    @Column(name = "PID")
    private String pid;
    /**
     * 추가속성1
     */
    @Column(name = "ATTR1")
    private String attr1;
    /**
     * 추가속성2
     */
    @Column(name = "ATTR2")
    private String attr2;
    /**
     * 추가속성3
     */
    @Column(name = "ATTR3")
    private String attr3;
    /**
     * 추가속성4
     */
    @Column(name = "ATTR4")
    private String attr4;
}
