package com.atonm.kblease.api.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;

@ConfigurationProperties(prefix = "schedule")
@Data
public class ScheduleConfig {
    private HashMap<String, HashMap<String, String>> kinds;
}
