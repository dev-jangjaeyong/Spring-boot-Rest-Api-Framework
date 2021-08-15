package com.atonm.kblease.api.common.entity;

import java.math.BigDecimal;
import java.sql.*;
import javax.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity(name = "entity.TaOrgination")
@Table(name = "TA_ORGINATION", schema="dbo", catalog="KBLease")
public class TaOrgination {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ORG_ID")
  private BigDecimal orgId;
  @Column(name = "COMPANY_NAME")
  private String companyName;
  @Column(name = "WORK_NO")
  private String workNo;
  @Column(name = "CHAIRMAN")
  private String chairman;
  @Column(name = "TEL")
  private String tel;
  @Column(name = "ADDR")
  private String addr;
  @Column(name = "CREATE_DT")
  private Timestamp createDt;
  @Column(name = "CREATE_UID")
  private BigDecimal createUid;
  @Column(name = "UPDATE_DT")
  private Timestamp updateDt;
  @Column(name = "UPDATE_UID")
  private BigDecimal updateUid;
}
