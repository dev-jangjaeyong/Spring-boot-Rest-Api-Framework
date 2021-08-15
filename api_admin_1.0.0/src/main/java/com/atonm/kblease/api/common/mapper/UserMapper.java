package com.atonm.kblease.api.common.mapper;

import com.atonm.kblease.api.config.datasource.annotation.Master;

@Master
public interface UserMapper {
    void updateLastLoginDt(String userId);
}
