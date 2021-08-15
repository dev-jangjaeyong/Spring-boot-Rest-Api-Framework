package com.atonm.kblease.api.common.service;

import com.atonm.kblease.api.utils.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate redisTemplate;

    /*
     * Redis 서버에 데이터를 저장합니다. 저정데이터는 json String 으로 저장해야합니다.
     *  redisKey:[redis key],   redisValue:[json String], duplicateAllowCheckYN:[중복으로 저장할 것인지 Y=update, N=update 안함]
     *  duplicateAllowCheckYN 이 "N"일경우 중복 저장을 허용하지 않기때문에, return false 로 처리함.              */
    public boolean RedisSet(String redisKey, String redisValue, String duplicateAllowCheckYN) {
        boolean successRedis = true;
        String redisGetValue = null;
        if(StringUtil.isEmpty(redisKey) || StringUtil.isEmpty(redisValue)|| StringUtil.isEmpty(duplicateAllowCheckYN)){
            successRedis = false;
        }else{
            if(duplicateAllowCheckYN.equalsIgnoreCase("Y")){
                redisTemplate.opsForValue().set(redisKey, redisValue);
            }else{
                redisGetValue = (String) redisTemplate.opsForValue().get(redisKey);
                if(redisGetValue != null && StringUtil.isEmpty(redisGetValue)){
                    successRedis = false;
                }else{
                    redisTemplate.opsForValue().set(redisKey, redisValue);
                }
            }
        }

        String checkValue = this.RedisGet(redisKey);
        System.out.println("#########################################################################################");
        System.out.println("REDIS : RedisSet and GET ");
        System.out.println("key : "+ redisKey +", checkValue = " + checkValue);
        System.out.println("");
        System.out.println("#########################################################################################");



        return successRedis;
    }
    /*
     *   넘어온 key값으로 redis 데이터를 조회 후 return 합니다.
     */
    public String RedisGet(String redisKey){
        String redisGetValue = (String) redisTemplate.opsForValue().get(redisKey);

        System.out.println("#########################################################################################");
        System.out.println("REDIS : RedisGet");
        System.out.println("key : "+ redisKey +", checkValue = " + redisGetValue);
        System.out.println("");
        System.out.println("#########################################################################################");

        return redisGetValue;
    }
}
