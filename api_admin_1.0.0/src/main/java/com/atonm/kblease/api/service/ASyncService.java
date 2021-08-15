package com.atonm.kblease.api.service;

import com.atonm.core.common.constant.Constant;
import com.atonm.kblease.api.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Async
@Slf4j
@Service
@RequiredArgsConstructor
public class ASyncService {

    /*
    * 비동기로 레디스  SET을 진행합니다.
    * 필요값에 따라 Overloading 해주세요
    * */

    public void aSyncSetRedis(String redisKey, String redisData) {
        try {
            RedisUtil.redisSet(redisKey, redisData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void aSyncSetRedis(String redisKey, List<Object> redisData) {
        try {
            RedisUtil.redisSet(redisKey, redisData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void aSyncSetRedis(String redisKey, Map<String, Object> redisData) {
        try {
            RedisUtil.redisSet(redisKey, redisData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
