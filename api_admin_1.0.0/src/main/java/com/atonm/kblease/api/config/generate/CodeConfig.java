package com.atonm.kblease.api.config.generate;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;

@ConfigurationProperties(prefix = "code-generate")
@Data
public class CodeConfig {
    private HashMap<String, String[]> codelist;
    private HashMap<String, HashMap<String, String>> ftp;
    private String serverType;
}
