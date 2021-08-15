package com.atonm.kblease.api.config.aop;

import com.atonm.kblease.api.utils.SecurityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorAwareImpl implements AuditorAware<Object> {
    @Override
    public Optional<Object> getCurrentAuditor() {
        return Optional.ofNullable(SecurityUtils.currentUserNo());
    }
}

@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@Configuration
class JpaAuditingConfig {
    @Bean
    AuditorAwareImpl auditorAware() {
        return new AuditorAwareImpl();
    }
}
