package com.atonm.kblease.api.utils;

import com.atonm.core.context.AppContextManager;
import com.atonm.kblease.api.common.entity.ResUserInfo;
import com.atonm.kblease.api.config.security.custom.SessionUser;
import com.atonm.kblease.api.repository.UserJpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;

/**
 * @author jang jea young
 * @since 2018-08-16
 */
public class SecurityUtils {
    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static boolean whetherLogin() {
        return getAuthentication() != null && !getAuthentication().getPrincipal().equals("anonymousUser");
    }

    public static String currentUserId() {
        return getCurrentUserInfo().getUserId();
    }

    private static UserJpaRepository getUserRepository() {
        return AppContextManager.getBean(UserJpaRepository.class);
    }

    public static SessionUser getCurrentUserInfo() {
        return ModelMapperUtils.map(getAuthentication().getPrincipal(), SessionUser.class);
    }

    public static BigDecimal currentUserNo() {
        return getCurrentUserInfo().getMid();
    }
}
