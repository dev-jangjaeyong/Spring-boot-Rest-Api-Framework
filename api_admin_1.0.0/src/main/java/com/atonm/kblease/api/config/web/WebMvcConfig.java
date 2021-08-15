package com.atonm.kblease.api.config.web;

import com.atonm.kblease.api.config.converter.YNConverter;
import com.atonm.kblease.api.config.property.AdditionalProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author jang jea young
 * @since 2018-08-09
 */
@Configuration
public class WebMvcConfig {
    private final AdditionalProperty property;

    @Autowired
    public WebMvcConfig(AdditionalProperty property) {
        this.property = property;
    }

    @Profile("prod")
    @Configuration
    public static class ProdMvcConfiguration implements WebMvcConfigurer {
        @Override
        public void addFormatters(FormatterRegistry registry) {
            registry.addConverter(new YNConverter());
        }

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            //registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE").allowedOrigins(properties.getAllowedOrigins());

            registry.addMapping("/**")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedOrigins("*")
                    .allowCredentials(true)
                    .maxAge(3600);
        }

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("swagger-ui.html")
                    .addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");

            registry.addResourceHandler("/webjars/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");

            registry.addResourceHandler("/swagger/**")
                    .addResourceLocations("classpath:/static/swagger/");

            registry.addResourceHandler("/gen/**")
                    .addResourceLocations("classpath:/gen/");
            /*registry.addResourceHandler("/gen/**")
                    .addResourceLocations("file:src/main/resources/gen/");*/
        }

        @Bean
        public StandardServletMultipartResolver multipartResolver() {
            /*
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
            multipartResolver.setMaxUploadSize(5 * 1024 * 1024);
            return multipartResolver;
            */
            return new StandardServletMultipartResolver();
        }
    }

    @Profile("dev")
    @Configuration
    public static class DevMvcConfiguration implements WebMvcConfigurer {
        @Override
        public void addFormatters(FormatterRegistry registry) {
            registry.addConverter(new YNConverter());
        }

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            //registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE").allowedOrigins(properties.getAllowedOrigins());

            registry.addMapping("/**")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedOrigins("*")
                    .allowCredentials(true)
                    .maxAge(3600);
        }

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("swagger-ui.html")
                    .addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");

            registry.addResourceHandler("/webjars/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");

            registry.addResourceHandler("/swagger/**")
                    .addResourceLocations("classpath:/static/swagger/");

            registry.addResourceHandler("/gen/**")
                    .addResourceLocations("file:src/main/resources/gen/");
        }

        @Bean
        public StandardServletMultipartResolver multipartResolver() {
            /*
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
            multipartResolver.setMaxUploadSize(5 * 1024 * 1024);
            return multipartResolver;
            */
            return new StandardServletMultipartResolver();
        }
    }

    @Profile("local")
    @Configuration
    public static class LocalMvcConfiguration implements WebMvcConfigurer {
        @Override
        public void addFormatters(FormatterRegistry registry) {
            registry.addConverter(new YNConverter());
        }

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            //registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE").allowedOrigins(properties.getAllowedOrigins());

            registry.addMapping("/**")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedOrigins("*")
                    .allowCredentials(true)
                    .maxAge(3600);
        }

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("swagger-ui.html")
                    .addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");

            registry.addResourceHandler("/webjars/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/");

            registry.addResourceHandler("/swagger/**")
                    .addResourceLocations("classpath:/static/swagger/");

            registry.addResourceHandler("/gen/**")
                    .addResourceLocations("file:src/main/resources/gen/");
        }

        @Bean
        public StandardServletMultipartResolver multipartResolver() {
            /*
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
            multipartResolver.setMaxUploadSize(5 * 1024 * 1024);
            return multipartResolver;
            */
            return new StandardServletMultipartResolver();
        }
    }
}
