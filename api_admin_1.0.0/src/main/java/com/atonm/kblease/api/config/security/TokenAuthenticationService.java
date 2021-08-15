package com.atonm.kblease.api.config.security;

import com.atonm.core.common.constant.Constant;
import com.atonm.kblease.api.common.base.UserRefreshTokenInfoDTO;
import com.atonm.kblease.api.common.base.UserRoleHrcDTO;
import com.atonm.kblease.api.common.entity.ResUserInfo;
import com.atonm.kblease.api.common.entity.UserRefreshTokenInfo;
import com.atonm.kblease.api.common.mapper.UserTokenMapper;
import com.atonm.kblease.api.config.CustomLocalDateTimeSerializer;
import com.atonm.kblease.api.config.property.AdditionalProperty;
import com.atonm.kblease.api.config.security.custom.SessionUser;
import com.atonm.kblease.api.dto.KbOrgInationDTO;
import com.atonm.kblease.api.log.dto.ActionLogDTO;
import com.atonm.kblease.api.log.mapper.LogMapper;
import com.atonm.kblease.api.utils.ModelMapperUtils;
import com.atonm.kblease.api.utils.SecurityUtils;
import com.atonm.core.api.ApiResponse;
import com.atonm.core.api.ApiStatus;
import com.atonm.core.context.AppContextManager;
import com.atonm.kblease.api.utils.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import static com.atonm.core.common.constant.Constant.*;
import static java.util.Collections.emptyList;

/**
 * @author jang jea young
 * @since 2018-07-30
 **/
class TokenAuthenticationService {
    private static final String SECRET = "ThisIsASecret";
    private static final String TOKEN_PREFIX = "KB_LEASE";

    static void addAuthentication(ActionLogDTO actionLogDTO, HttpServletRequest request, HttpServletResponse response, Authentication authentication, LogMapper logMapper, String userTyCd, UserTokenMapper userTokenMapper, String encrytUserPassword, boolean isRememberMe) throws IOException {
        ApiResponse apiResponse = new ApiResponse();
        // Security session에 추가 정보(Cusom)를 저장한다(Map형태)
        ResUserInfo resUserInfo = (ResUserInfo)authentication.getPrincipal();
        resUserInfo.setLastLoginDt(null);

        ObjectMapper mapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(LocalDateTime.class, new CustomLocalDateTimeSerializer());
        mapper.registerModule(simpleModule);

        UserRoleHrcDTO userRoleHrcDTO = new UserRoleHrcDTO();
        Object[] roles = resUserInfo.getRoleTypes().toArray();
        userRoleHrcDTO.setRoleName(String.valueOf(roles[0]));
        userRoleHrcDTO = userTokenMapper.findUserHierarchyRoles(userRoleHrcDTO);
        resUserInfo.setRoleHierarchy(StringUtil.replaceAll(userRoleHrcDTO.getRoleHierarchy(), ",", ">"));

        String jsonInUserInfo = mapper.writeValueAsString(resUserInfo);

        String AUTHENTICATION_TOKEN = addHeader(response, jsonInUserInfo);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding(UTF_8);

        String uid = StringUtil.uuid();
        apiResponse = ApiResponse.login(AUTHENTICATION_TOKEN, uid);

        // set Log
        try{
            createRefreshToken(jsonInUserInfo, resUserInfo, userTokenMapper, isRememberMe, uid);
        	ActionLogDTO logObj = getLogObject(ApiStatus.SUCCESS, actionLogDTO, resUserInfo, new ObjectMapper().writeValueAsString(apiResponse));
            logMapper.insertActionLog(logObj);
            logObj.setMId(resUserInfo.getMId());
            logObj.setUserId(resUserInfo.getUserId());
            logObj.setLoginSucYn(logObj.getResultCode().equalsIgnoreCase("S001") ? "Y" : "N");
            logMapper.insertLoginLog(logObj);
        }catch (Exception e) {
            logMapper.insertActionLog(getLogObject(ApiStatus.SERVER_ERROR, actionLogDTO, resUserInfo, new ObjectMapper().writeValueAsString(apiResponse)));
        }
        // set log end
        SecurityContext sec = SecurityContextHolder.getContext();
        AbstractAuthenticationToken auth = (AbstractAuthenticationToken)sec.getAuthentication();
        auth = new UsernamePasswordAuthenticationToken(resUserInfo, authentication.getCredentials(), resUserInfo.getAuthorities());
        auth.setDetails(resUserInfo);

        response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
    }

    /**
     * 인증 성공시, 토큰 생성 후 헤더에 값을 추가한다.
     */
    private static String addHeader(HttpServletResponse response, String jsonInUserInfo) throws JsonProcessingException {
        final int EXPIRATION_TIME = AppContextManager.getBean(AdditionalProperty.class).getSessionTimeout();

        String JWT = Jwts.builder()
                .setSubject(jsonInUserInfo)
                .setExpiration(new Date(System.currentTimeMillis() + 60000))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        String AUTHENTICATION_TOKEN = TOKEN_PREFIX + " " + JWT;

        response.addHeader(ACCESS_TOKEN_HEADER_NAME, AUTHENTICATION_TOKEN);

        return AUTHENTICATION_TOKEN;
    }

    /**
     * 재요청시 인증되었는지 확인한다.
     */
    static Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response, UserTokenMapper userTokenMapper) throws IOException {
        String token = request.getHeader(ACCESS_TOKEN_HEADER_NAME);
        String user = null;

        if (token != null) {
            // parse the token.

            String TOKEN_VALUE = token.replace(TOKEN_PREFIX + ASCII_EMPTY_SPACE, EMPTY_STRING).replace(TOKEN_PREFIX, EMPTY_STRING);

            if(EMPTY_STRING.equals(TOKEN_VALUE)) {
                TOKEN_VALUE = "\"\"";
            }

            try{
                user = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(TOKEN_VALUE)
                        .getBody()
                        .getSubject();
                response.addHeader(ACCESS_TOKEN_HEADER_NAME, token);
            } catch (ExpiredJwtException e) {
                if(SecurityUtils.whetherLogin()) {
                    ObjectMapper mapper = new ObjectMapper();
                    user = mapper.writeValueAsString(SecurityUtils.getCurrentUserInfo());
                    addHeader(response, user);
                }else{
                    user = getNewTokenFromRefreshToken(request, response, userTokenMapper, user);
                }
            }

            ObjectMapper mapper = new ObjectMapper();

            if(user != null) {
                SessionUser sessionUser = mapper.readValue(user, SessionUser.class);
                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                for (HashMap<String, Object> auth : sessionUser.getAuthorities())
                    authorities.add(new SimpleGrantedAuthority ((String) auth.get("roleName")));

                return new UsernamePasswordAuthenticationToken(mapper.readValue(user, SessionUser.class), null, authorities);
            }else return null;
        } else {
            return null;
        }
    }

    private static ActionLogDTO getLogObject(ApiStatus apiStatus, ActionLogDTO actionLogDTO, ResUserInfo sessionUser, String tokenResult) {
        actionLogDTO.setUserId(sessionUser.getMId().toString());
        actionLogDTO.setPid(Constant.PID);
        actionLogDTO.setResultCode(apiStatus.getValue().get(apiStatus.toString()));
        actionLogDTO.setResultStatus(apiStatus.toString());
        actionLogDTO.setReqArgs(actionLogDTO.getReqArgs());
        actionLogDTO.setResMsg(tokenResult);
        actionLogDTO.setResDt(LocalDateTime.now());

        return actionLogDTO;
    }


    private static String createRefreshToken(String jsonInUserInfo, ResUserInfo resUserInfo, UserTokenMapper userTokenMapper, boolean isAutoLogin, String uid) throws JsonProcessingException {
        Long activeMilisecond = isAutoLogin ? 432000000L : 86400000L;

        String JWT = Jwts.builder()
                .setSubject(jsonInUserInfo)
                .setExpiration(new Date(System.currentTimeMillis() + activeMilisecond))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        String AUTHENTICATION_TOKEN = TOKEN_PREFIX + " " + JWT;

        UserRefreshTokenInfoDTO info = new UserRefreshTokenInfoDTO(resUserInfo.getMId(), uid, "", AUTHENTICATION_TOKEN);
        if(userTokenMapper.selectUserToken(info) != null) {
            userTokenMapper.deleteUserToken(info);
        }
        userTokenMapper.insertUserToken(info);

        return AUTHENTICATION_TOKEN;
    }



    /**
     * refresh token을 조회합니다.
     * @param request
     * @param response
     * @param userTokenMapper
     * @param user
     * @return
     * @throws IOException
     */
    private static String getNewTokenFromRefreshToken(HttpServletRequest request, HttpServletResponse response, UserTokenMapper userTokenMapper, String user) throws IOException {
        try{
            UserRefreshTokenInfoDTO userRefreshTokenInfoDTO = new UserRefreshTokenInfoDTO();
            //userRefreshTokenDTO.setAccessToken(token);
            String uid = getUid(request);

            if(!StringUtil.isEmpty(uid)) {
                userRefreshTokenInfoDTO.setSId(uid);
                userRefreshTokenInfoDTO.setPid("");
                userRefreshTokenInfoDTO = userTokenMapper.selectUserToken(userRefreshTokenInfoDTO);

                if(userRefreshTokenInfoDTO != null && !StringUtil.isEmpty(userRefreshTokenInfoDTO.getMToken())) {
                    String REFRESH_TOKEN = userRefreshTokenInfoDTO.getMToken();

                    REFRESH_TOKEN = REFRESH_TOKEN.replace(TOKEN_PREFIX + ASCII_EMPTY_SPACE, EMPTY_STRING).replace(TOKEN_PREFIX, EMPTY_STRING);

                    if(!StringUtil.isEmpty(REFRESH_TOKEN)) {
                        user = Jwts.parser()
                                .setSigningKey(SECRET)
                                .parseClaimsJws(REFRESH_TOKEN)
                                .getBody()
                                .getSubject();

                        addHeader(response, user);
                    }
                }else{
                    throw new ExpiredJwtException(null, null, ApiStatus.EXPIRED_TOKEN.getValue().toString());
                }
            }
        }
        catch(JpaSystemException ex1) {
            throw new ExpiredJwtException(null, null, ApiStatus.EXPIRED_TOKEN.getValue().toString());
        }catch (ExpiredJwtException ex2) {
            throw new ExpiredJwtException(ex2.getHeader(), ex2.getClaims(), ApiStatus.EXPIRED_TOKEN.getValue().toString());
        }
        return user;
    }


    private static boolean getAutoLogin(HttpServletRequest request) {
        boolean isAutoLogin = false;
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("_a")) {
                    isAutoLogin = cookie.getValue().equalsIgnoreCase("1".toString());
                    break;
                }
            }
        }

        return isAutoLogin;
    }

    private static String getUid(HttpServletRequest request) {
        String uid = "";
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(REFRESH_TOKEN_UID_HEADER_NAME)) {
                    uid = cookie.getValue();
                    break;
                }
            }
        }

        if(StringUtil.isEmpty(uid)) {
            uid = request.getHeader(REFRESH_TOKEN_UID_HEADER_NAME);
        }

        return StringUtil.isEmpty(uid) ? "" : uid;
    }
}
