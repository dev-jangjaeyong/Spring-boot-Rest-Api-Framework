package com.atonm.kblease.api.common.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * 매물정보공유로그
 */
@Entity
@Table(name = "car_sale_info_share_log")
@Getter
@Setter
public class Share {
    /**
     * 로그순번
     */
    @Id
    @Column(name = "LOG_NO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "code_sq_generator")
    @SequenceGenerator(name="code_sq_generator", sequenceName = "sq_car_sale_info_share_log", allocationSize=1)
    private Long logNo;
    /**
     * PID
     */
    @Column(name = "PID")
    private String pid;
    /**
     * SEND_USER_NO
     */
    @Column(name = "SEND_USER_NO")
    private Integer sendUserNo;
    /**
     * 채널구분
     */
    @Column(name = "CHANNEL_TY_CD")
    private String channelTyCd;
    /**
     * 채널값
     */
    @Column(name = "CHANNEL_VALUE")
    private String channelValue;
    /**
     * URL
     */
    @Column(name = "URL")
    private String url;
    /**
     * 공유일시
     */
    @Column(name = "SHARE_ST_DT")
    private LocalDate shareStDt;
    /**
     * 만료일시
     */
    @Column(name = "SHARE_ED_DT")
    private LocalDate shareEdDt;
    /**
     * 공유가격
     */
    @Column(name = "SHARE_PRICE")
    private Long sharePrice;
    /**
     * 공유설명
     */
    @Column(name = "SHARE_DSCRP")
    private String shareDscrp;
    /**
     * 조회COUNT
     */
    @Column(name = "VIEW_CNT")
    private Long viewCnt;
    /**
     * 가격노출여부
     */
    @Column(name = "PRICE_OPEN_YN")
    private String priceOpenYn;
    /**
     * 담당자노출여부
     */
    @Column(name = "USER_OPEN_YN")
    private String userOpenYn;
    /**
     * 차량정보번호
     */
    @Column(name = "CAR_NO")
    private String carNo;
}
