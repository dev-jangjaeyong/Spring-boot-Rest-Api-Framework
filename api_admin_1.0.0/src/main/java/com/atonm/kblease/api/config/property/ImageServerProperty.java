package com.atonm.kblease.api.config.property;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "image-server")
@Data
public class ImageServerProperty {

    @Value("${image-server.url}")
    private String url;


    @Value("${image-server.username}")
    private String username;


    @Value("${image-server.password}")
    private String password;


    @Value("${image-server.path}")
    private String path;


    @Value("${image-server.port}")
    private String port;


}
