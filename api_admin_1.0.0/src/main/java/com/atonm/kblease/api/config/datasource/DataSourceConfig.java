package com.atonm.kblease.api.config.datasource;

import com.atonm.kblease.api.config.datasource.properties.*;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author jang jea young
 * @since 2018-11-09
 */
public abstract class DataSourceConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfig.class);

    @Bean
    public abstract DataSource dataSource();

    protected HikariConfig configureDataSource(HikariConfig dataSourceConfig, DataSourceProperties databaseProperties) {
        LOGGER.info("configureDataSource = {}", databaseProperties);
        dataSourceConfig.setDriverClassName(databaseProperties.getDriverClassName());
        dataSourceConfig.setJdbcUrl(databaseProperties.getUrl());
        dataSourceConfig.setUsername(databaseProperties.getUserName());
        dataSourceConfig.setPassword(databaseProperties.getPassword());
        dataSourceConfig.setMaximumPoolSize(databaseProperties.getMaxActive());
        dataSourceConfig.setMinimumIdle(databaseProperties.getMinIdle());
        dataSourceConfig.setValidationTimeout(60000);
        dataSourceConfig.setMaxLifetime(1800000);
        //dataSourceConfig.setConnectionTestQuery(databaseProperties.getValidationQuery());
        //dataSourceConfig.setConnectionTestQuery("select 1");
        dataSourceConfig.setLeakDetectionThreshold(60000);
        dataSourceConfig.setIdleTimeout(60000);
        dataSourceConfig.addDataSourceProperty("cachePrepStmts", true);
        dataSourceConfig.addDataSourceProperty("prepStmtCacheSize", 256);
        dataSourceConfig.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        dataSourceConfig.addDataSourceProperty("useServerPrepStmts", true);

        return dataSourceConfig;
        //setMaxIdle(databaseProperties.getMaxIdle());
        /*dataSource.setMinIdle(databaseProperties.getMinIdle());
        dataSource.setMaxWait(databaseProperties.getMaxWait());
        dataSource.setValidationQuery(databaseProperties.getValidationQuery());
        dataSource.setTestWhileIdle(true);

        dataSource.setTimeBetweenEvictionRunsMillis(60000);*/
    }
}


@Configuration
@EnableTransactionManagement
@EnableConfigurationProperties(MasterDataSourceProperties.class)
class MasterDatabaseConfig extends DataSourceConfig {

    private final MasterDataSourceProperties masterDatabaseProperties;

    @Autowired
    public MasterDatabaseConfig(MasterDataSourceProperties masterDatabaseProperties) {
        this.masterDatabaseProperties = masterDatabaseProperties;
    }

    @Primary
    @Bean(name = "masterDataSource", destroyMethod = "close")
    public DataSource dataSource() {
        /*HikariDataSource masterDataSource = DataSourceBuilder.create().type(HikariDataSource.class).build();*/
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig = configureDataSource(hikariConfig, masterDatabaseProperties);
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }
}

@Configuration
@EnableConfigurationProperties(SecondaryDataSourceProperties.class)
class SecondaryDataSourceConfig extends DataSourceConfig {

    private final SecondaryDataSourceProperties secondaryDataSourceProperties;

    @Autowired
    public SecondaryDataSourceConfig(SecondaryDataSourceProperties secondaryDataSourceProperties) {
        this.secondaryDataSourceProperties = secondaryDataSourceProperties;
    }

    @Bean(name = "secondaryDataSource", destroyMethod = "close")
    public DataSource dataSource() {
        /*HikariDataSource secondaryDataSource = DataSourceBuilder.create().type(HikariDataSource.class).build();
        //org.apache.tomcat.jdbc.pool.DataSource secondaryDataSource = new org.apache.tomcat.jdbc.pool.DataSource();
        configureDataSource(secondaryDataSource, secondaryDataSourceProperties);
        return secondaryDataSource;*/
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig = configureDataSource(hikariConfig, secondaryDataSourceProperties);
        return new HikariDataSource(hikariConfig);
    }
}
