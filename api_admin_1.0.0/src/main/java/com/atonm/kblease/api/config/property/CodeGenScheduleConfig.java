package com.atonm.kblease.api.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;


@ConfigurationProperties(prefix = "code-generate.schedule")
@Data
public class CodeGenScheduleConfig {
    private String  availability;
    private String  jsfilePath;
    private String  jsfilePathLocal;
    private String  jsfilePathDev;
    private String  jsfilePathLive;
    private String  jsfilePathLiveSecond;

    private HashMap<String, String> partAvailability;

    public String getJsfilePath ( String serverType ) {
        String filePath =   "";
        if ( serverType.equals("local") ) {
            filePath    =   this.getJsfilePathLocal();
        } else if ( serverType.equals("dev") ) {
            filePath    =   this.getJsfilePathDev();
        } else if ( serverType.equals("live") ) {
            filePath    =   this.getJsfilePathLive();
        } else if ( serverType.equals("live-second") ) {
            filePath    =   this.getJsfilePathLiveSecond();
        }
        return filePath;
    }

}
