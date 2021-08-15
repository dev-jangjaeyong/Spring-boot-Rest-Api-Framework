package com.atonm.kblease.api;

import com.atonm.excel.ExcelDownload;
import com.atonm.kblease.api.config.GracefulShutdown;
import com.atonm.kblease.api.config.generate.CodeConfig;
import com.atonm.kblease.api.config.property.CodeGenScheduleConfig;
import com.atonm.kblease.api.config.property.ScheduleConfig;
import com.atonm.core.CoreConfiguration;
import com.atonm.kblease.api.config.datasource.properties.MasterDataSourceProperties;
import com.atonm.kblease.api.config.datasource.properties.SecondaryDataSourceProperties;
import com.atonm.kblease.api.config.property.AdditionalProperty;
import com.atonm.kblease.api.file.fileStorageUtil.property.FileStorageUtilProperties;
import com.atonm.kblease.api.config.property.YamlPropertySourceFactory;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

//@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@SpringBootApplication
@Configuration
@EnableConfigurationProperties  (
        {   ScheduleConfig.class,
            CodeGenScheduleConfig.class,
            CodeConfig.class,
            AdditionalProperty.class,
            MasterDataSourceProperties.class,
            SecondaryDataSourceProperties.class,
            FileStorageUtilProperties.class
        }
    )
//@PropertySource(value = {"classpath:database.yml", "classpath:code.yml"}, factory = YamlPropertySourceFactory.class)
@PropertySource(value = {"classpath:code.yml"}, factory = YamlPropertySourceFactory.class)
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan({"com.atonm.kblease", "com.atonm.excel", "com.atonm.core", "com.atonm.schedule"})
@EnableScheduling
@EnableCaching
@Import(CoreConfiguration.class)
public class ApiApplication extends SpringBootServletInitializer {
    @Autowired
    ScheduleConfig scheduleConfig;

    @Autowired
    CodeGenScheduleConfig codeGenScheduleConfig;

    @Autowired
    CodeConfig codeConfig;

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    @Bean
    public GracefulShutdown gracefulShutdown() {
        return new GracefulShutdown();
    }

    @Bean
    public ConfigurableServletWebServerFactory webServerFactory(final GracefulShutdown gracefulShutdown) {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(gracefulShutdown);
        return factory;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper;
    }

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);

        return loggingFilter;
    }

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
	    CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }

    @Bean
    public ExcelDownload excelView() {
        return new ExcelDownload(codeConfig);
    }
}
