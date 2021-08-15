package com.atonm.kblease.api.config.datasource.properties;

/**
 * @author jang jea young
 * @since 2018-11-09
 */
public interface DataSourceProperties {
    public String getDriverClassName();

    public String getUrl();

    public String getUserName();

    public String getPassword();

    public int getInitialSize();

    public int getMaxActive();

    public int getMaxIdle();

    public int getMinIdle();

    public int getMaxWait();

    public String getValidationQuery();
}
