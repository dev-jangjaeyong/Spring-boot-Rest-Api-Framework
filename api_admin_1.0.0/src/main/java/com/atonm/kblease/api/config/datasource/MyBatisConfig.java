package com.atonm.kblease.api.config.datasource;

import com.atonm.kblease.api.config.datasource.annotation.Master;
import com.atonm.kblease.api.config.datasource.annotation.Secondary;
import com.atonm.kblease.api.config.typeHandler.EmptyStringIfNullTypeHandller;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @author jang jea young
 * @since 2018-11-09
 */
public abstract class MyBatisConfig {
    public static final String BASE_PACKAGE = "com.atonm.kblease.api.*/**";
    public static final String CONFIG_LOCATION_PATH = "classpath:mybatis/mybatis-conf.xml";
    public static final String MAPPER_LOCATIONS_PATH = "mapper/**/**.xml";

    protected void configureSqlSessionFactory(SqlSessionFactoryBean sessionFactoryBean, DataSource dataSource) throws IOException {
        PathMatchingResourcePatternResolver pathResolver = new PathMatchingResourcePatternResolver();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setConfigLocation(pathResolver.getResource(CONFIG_LOCATION_PATH));
        sessionFactoryBean.setMapperLocations(pathResolver.getResources(MAPPER_LOCATIONS_PATH));
        sessionFactoryBean.setTypeHandlers(new TypeHandler[] {
                new EmptyStringIfNullTypeHandller()
        });
        sessionFactoryBean.setVfs(SpringBootVFS.class);  // Spring Boot 전용 VFS 사용하도록 지정
        sessionFactoryBean.setTypeAliasesPackage(BASE_PACKAGE);
    }
}

@Configuration
@MapperScan(basePackages = MyBatisConfig.BASE_PACKAGE, annotationClass = Master.class, sqlSessionFactoryRef = "masterSqlSessionFactory")
class MasterMyBatisConfig extends MyBatisConfig {
    @Bean
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("masterDataSource") DataSource masterDataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        configureSqlSessionFactory(sessionFactoryBean, masterDataSource);

        return sessionFactoryBean.getObject();
    }
}

@Configuration
@MapperScan(basePackages = MyBatisConfig.BASE_PACKAGE, annotationClass = Secondary.class, sqlSessionFactoryRef = "secondarySqlSessionFactory")
class SecondaryMyBatisConfig extends MyBatisConfig {
    @Bean
    public SqlSessionFactory secondarySqlSessionFactory(@Qualifier("secondaryDataSource") DataSource secondaryDataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        configureSqlSessionFactory(sessionFactoryBean, secondaryDataSource);
        return sessionFactoryBean.getObject();
    }
}
