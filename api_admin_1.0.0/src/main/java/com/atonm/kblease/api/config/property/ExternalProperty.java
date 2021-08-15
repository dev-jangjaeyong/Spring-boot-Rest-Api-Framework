package com.atonm.kblease.api.config.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "external")
@Data
public class ExternalProperty {
    /*******************************************************************************************************************
     * added by 2021-04-08 JEONG INJIN
     ******************************************************************************************************************/
    @Value("${external.alimtalk.sendurl}")
    private String alimtalkSendUrl;
    @Value("${external.alimtalk.apreadsendurl}")
    private String apReadAlimtalkSendUrl;

}
