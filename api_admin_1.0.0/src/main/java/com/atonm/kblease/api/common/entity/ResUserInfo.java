package com.atonm.kblease.api.common.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author jang jea young
 * @since 2018-08-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ResUserInfo  extends TaMember implements UserDetails {
    private static final long serialVersionUID = 8666468119299100306L;

    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;

    private String roleHierarchy;

    public ResUserInfo(TaMember taMember) {
        super();
        setMId(taMember.getMId());
        setOrgId(taMember.getOrgId());
        setUserId(taMember.getUserId());
        setUserPw(taMember.getUserPw());
        setUserHp(taMember.getUserHp());
        setMemberName(taMember.getMemberName());
        setUserRoles(taMember.getUserRoles());
        setLastLoginDt(taMember.getLastLoginDt());
        setTaOrgination(taMember.getTaOrgination());
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
    }

    public Set<UserRole.RoleType> getRoleTypes() {
        return getUserRoles().stream().map(f -> f.getRoleName()).collect(Collectors.toSet());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getUserRoles();
    }

    @Override
    public String getPassword() {
        return this.getUserPw();
    }

    @Override
    public String getUsername() {
        return this.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
