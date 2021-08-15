package com.atonm.kblease.api.common.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.*;


import com.atonm.kblease.api.common.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.apache.tomcat.jni.Local;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author jang jea young
 * @since 2018-08-09
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "entity.TaMember")
@Table(name = "TA_MEMBER", schema="dbo", catalog="KBLease")
@SecondaryTable(name = "TA_ORGINATION", schema = "dbo", catalog="KBLease",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "ORG_ID"))
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate @DynamicInsert
public class TaMember extends BaseEntity implements Serializable {
  private static final long serialVersionUID = -563329217866858622L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "M_ID")
  private BigDecimal mId;
  @Column(name = "ORG_ID")
  private BigDecimal orgId;
  @Column(name = "USER_ID")
  private String userId;
  @Column(name = "USER_PW")
  private String userPw;
  @Column(name = "USER_NAME")
  private String memberName;
  @Column(name = "USER_HP")
  private String userHp;
  @Column(name = "LAST_LOGIN_DT")
  @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
  private LocalDateTime lastLoginDt;
  /*@Column(name = "CREATE_DT")
  private LocalDateTime createDt;
  @Column(name = "CREATE_UID")
  private BigDecimal createUid;
  @Column(name = "UPDATE_DT")
  private LocalDateTime updateDt;
  @Column(name = "UPDATE_UID")
  private BigDecimal updateUid;*/

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name="ORG_ID", insertable = false, updatable = false)
  private TaOrgination taOrgination;

  @Singular("userRoles")
  @JsonIgnoreProperties({"UPDATE_DT", "CREATE_DT"})
  @JsonManagedReference
  @OneToMany(mappedBy = "member")
  private Set<UserRole> userRoles;

  @Builder
  public TaMember(BigDecimal orgId, String userid, String userHp, LocalDateTime lastLoginDt) {
    this.orgId = orgId;
    this.userId = userid;
    this.userHp = userHp;
    this.lastLoginDt = lastLoginDt;
  }
}
