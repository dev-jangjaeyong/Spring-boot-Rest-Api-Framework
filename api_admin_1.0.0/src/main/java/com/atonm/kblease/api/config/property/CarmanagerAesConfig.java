package com.atonm.kblease.api.config.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "carmanager-aes")
@Data
public class CarmanagerAesConfig {
    private String key;
    private String iv;

    @Value("${carmanager-aes.ase256.pincrux.key}")
    private String pincruxKey;
    @Value("${carmanager-aes.ase256.pincrux.vi}")
    private String pincruxVi;


    @Value("${carmanager-aes.ase256.ajc.key}")
    private String ajcKey;
    @Value("${carmanager-aes.ase256.ajc.vi}")
    private String ajcVi;


    @Value("${carmanager-aes.ase256.aton.key}")
    private String atonKey;
    @Value("${carmanager-aes.ase256.aton.vi}")
    private String atonVi;

    /*******************************************************************************************************************
     * added by 2020-01-08 온병옥
     *      ATON PUSH 발송용
     ******************************************************************************************************************/
    @Value("${carmanager-aes.ase256.aton-push.key}")
    private String atonPushKey;
    @Value("${carmanager-aes.ase256.aton-push.vi}")
    private String atonPushVi;


}
