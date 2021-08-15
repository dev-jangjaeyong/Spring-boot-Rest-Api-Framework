package com.atonm.kblease.api.config.security.custom;

import com.atonm.kblease.api.common.entity.TaOrgination;
import com.atonm.kblease.api.common.entity.UserRole;
import com.atonm.kblease.api.dto.KbOrgInationDTO;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author jang jea young
 * @since 2018-08-09
 */
@Data
public class SessionUser {
    private BigDecimal mid;
    private BigDecimal orgId;
    private String userId;
    private String userPw;
    private String memberName;
    private String userHp;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime lastLoginDt;
    private KbOrgInationDTO taOrgination;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private ArrayList<HashMap<String, Object>> userRoles;
    private ArrayList<HashMap<String, Object>> authorities;
    private ArrayList<String> roleTypes;
    private String createDt;
    private String updateDt;
    private BigDecimal createUserNo;
    private BigDecimal updateUserNo;
    private String password;
    private String username;
    private String roleHierarchy;
}
