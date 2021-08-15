package com.atonm.kblease.api.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.common.base.CaseFormat;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomNamingStrategy extends PropertyNamingStrategy.PropertyNamingStrategyBase {
    /**
     *
     */
    private static final long serialVersionUID = -6456981617015946114L;

    @Override
    public String translate(String propertyName) {
        String result = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_CAMEL, propertyName);
        return result;
    }
}
