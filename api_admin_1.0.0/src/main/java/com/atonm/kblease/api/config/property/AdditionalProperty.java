package com.atonm.kblease.api.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author jang jea young
 * @since 2018-08-10
 */
@ConfigurationProperties(prefix = "custom")
@Data
public class AdditionalProperty {
    private String[] allowedOrigins;
    private int sessionTimeout = 60; // 1 minute
    private boolean develop;
}
