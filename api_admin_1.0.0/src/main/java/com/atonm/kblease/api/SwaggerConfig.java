package com.atonm.kblease.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.atonm.core.common.constant.Constant;
import com.google.common.collect.Lists;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
    	Docket docket =  new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    	
    	docket.securitySchemes(Lists.newArrayList(apiKey()));
    	
        return docket;
    }
    
    private ApiKey apiKey() {
        return new ApiKey("JWT", Constant.ACCESS_TOKEN_HEADER_NAME, "header");
    }
    
    
}
