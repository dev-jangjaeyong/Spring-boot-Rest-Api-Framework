package com.atonm.kblease.api.utils;

import com.atonm.core.api.ApiStatus;
import com.atonm.core.common.constant.Constant;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jang jea young
 * @since 2021-05-01
 */
@Component
public class AlimTokenUtil {
    private static final String SECRET = "ThisIsASecret";

    /**
     * token 생성
     * @param payload
     * @return
     * @throws JsonProcessingException
     */
    public static String createToken(HashMap<String, String> payload) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInfo = mapper.writeValueAsString(payload);
        return (Jwts.builder()
                .setSubject(jsonInfo)
                .setExpiration(new Date(System.currentTimeMillis() + 604800000L))
                //.setExpiration(new Date(System.currentTimeMillis() + 60000))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact()).replaceAll("\\.","__");
    }

    /**
     * token으로 부터 정보를 취득합니다.
     * @param token
     * @return
     */
    public static HashMap<String, Object> getTokenInfo(String token) {
        HashMap __retMap = new HashMap<String, Object>();
        String __t = null;
        token = token.replaceAll("__",".");
        try{
            __t =  Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            __retMap.put("EXPIRE_CHECK", ApiStatus.SUCCESS);
            __retMap.put("PAYLOAD", new ObjectMapper().readValue(__t, HashMap.class));
        }catch (ExpiredJwtException | IOException e) {
            __retMap.put("EXPIRE_CHECK", ApiStatus.EXPIRED_TOKEN);
        }

        return __retMap;
    }
}
