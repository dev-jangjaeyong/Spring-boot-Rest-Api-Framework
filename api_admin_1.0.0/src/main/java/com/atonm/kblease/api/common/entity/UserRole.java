package com.atonm.kblease.api.common.entity;

import com.atonm.kblease.api.common.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name="TA_MEMBER_ROLE", uniqueConstraints = {@UniqueConstraint(columnNames = {"M_ID", "ROLE_NAME"})})
@DynamicUpdate
public class UserRole implements GrantedAuthority {
    /*@Value("${role.list}")
    @Enumerated(EnumType.STRING)
    public RoleType roleList;*/

    private static final long serialVersionUID = 7943607393308984161L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROLE_ID")
    private BigDecimal roleId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "M_ID", nullable = false, foreignKey = @ForeignKey(name = "FK_TA_MEMBER_ROLE_TA_MEMBER"))
    private TaMember member;

    @Column(name = "ROLE_NAME", nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private RoleType roleName;

    public enum RoleType {
        ROLE_SUPER, ROLE_ADMIN, ROLE_KB_ADMIN, ROLE_KB_USER, ROLE_USER
    }

    @JsonIgnore
    @Override
    public String getAuthority() {
        return this.roleName.name();
    }
}
