package com.atonm.kblease.api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jang jea young
 * @since 2021-05-13
 */
@Component
public class RedisUtil {
    private static RedisTemplate redisTemplate;
    private static String keyprefix;

    @Qualifier("redisTemplate")
    @Autowired
    private RedisTemplate fRedisTemplate;

    @PostConstruct
    public void init() {
        keyprefix = "";
        redisTemplate = fRedisTemplate;
    }

    /**
     * 저장된 value를 String으로 반환한다.
     * @param k : key
     * @return
     */
    public static String redisGet(String k) {
        return String.valueOf(redisTemplate.opsForValue().get(keyprefix + k));
    }

    /**
     * 저장된 value를 JsonObject로 반환한다.
     * @param k : key
     * @return
     */
    public static JsonObject redisGetJson(String k) {
        ObjectMapper _o = new ObjectMapper();
        return _o.convertValue(String.valueOf(redisTemplate.opsForValue().get(keyprefix + k)), JsonObject.class);
    }

    /**
     * 저장된 value를 Map으로 반환한다.
     * @param k : key
     * @return
     */
    public static HashMap redisGetMap(String k) {
        ObjectMapper _o = new ObjectMapper();
        return _o.convertValue(String.valueOf(redisTemplate.opsForValue().get(keyprefix + k)), HashMap.class);
    }

    /**
     * key와 value를 저장한다.
     * @param k : key(String)
     * @param v : value(String)
     * @return
     */
    public static boolean redisSet(String k, String v) {
        if(StringUtil.isEmpty(k) || StringUtil.isEmpty(v)) return false;
        redisTemplate.opsForValue().set(keyprefix + k, v);
        return !StringUtil.isEmpty(String.valueOf(redisTemplate.opsForValue().get(keyprefix + k)));
    }

    /**
     * key와 value를 저장한다.
     * @param k : key(String)
     * @param v : value(Map)
     * @return
     */
    public static boolean redisSet(String k, Map<String, Object> v)  {
        ObjectMapper _o = new ObjectMapper();
        if(StringUtil.isEmpty(k) || v == null || v.size() == 0) return false;
        try {
            redisTemplate.opsForValue().set(keyprefix + k, _o.writeValueAsString(v));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return !StringUtil.isEmpty(String.valueOf(redisTemplate.opsForValue().get(keyprefix + k)));
    }

    /**
     * key와 value를 저장한다.
     * @param k : key(String)
     * @param v : value(List)
     * @return
     */
    public static boolean redisSet(String k, List<Object> v) {
        ObjectMapper _o = new ObjectMapper();
        if(StringUtil.isEmpty(k) || v == null || v.size() == 0) return false;
        try {
            redisTemplate.opsForValue().set(keyprefix + k, _o.writeValueAsString(v));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return !StringUtil.isEmpty(String.valueOf(redisTemplate.opsForValue().get(keyprefix + k)));
    }

   /**
     * key와 value를 저장한다.(중복저장을 방지한다.)
     * @param k : key(String)
     * @param v : value(String)
     * @return
     */
    public static boolean redisSetAvoidDupl(String k, String v) {
        return !redisTemplate.hasKey(keyprefix + k) && redisSet(keyprefix + k, v);
    }

    /**
     * key와 value를 저장한다.(중복저장을 방지한다.)
     * @param k : key(String)
     * @param v : value(Map)
     * @return
     */
    public static boolean redisSetAvoidDupl(String k, Map<String, Object> v) {
        return !redisTemplate.hasKey(keyprefix + k) && redisSet(keyprefix + k, v);
    }

    /**
     * key와 value를 저장한다.(중복저장을 방지한다.)
     * @param k : key(String)
     * @param v : value(List)
     * @return
     */
    public static boolean redisSetAvoidDupl(String k, List<Object> v) {
        return !redisTemplate.hasKey(keyprefix + k) && redisSet(keyprefix + k, v);
    }
}
