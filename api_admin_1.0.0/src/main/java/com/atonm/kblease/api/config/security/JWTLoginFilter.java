package com.atonm.kblease.api.config.security;

import com.atonm.kblease.api.common.mapper.UserMapper;
import com.atonm.kblease.api.common.mapper.UserTokenMapper;
import com.atonm.kblease.api.config.property.CarmanagerAesConfig;
import com.atonm.kblease.api.log.dto.ActionLogDTO;
import com.atonm.kblease.api.log.mapper.LogMapper;
import com.atonm.kblease.api.repository.UserJpaRepository;
import com.atonm.kblease.api.utils.AES256Util;
import com.atonm.core.api.ApiResponse;
import com.atonm.core.common.constant.Constant;
import com.atonm.core.context.AppContextManager;
import com.atonm.core.interceptor.ClientInfo;
import com.atonm.kblease.api.utils.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Collections;

/**
 * @author jang jea young
 * @since 2018-07-30
 **/
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {
    private CarmanagerAesConfig carmanagerAesConfig;

    public String userId;
    private UserJpaRepository userJpaPository;
    private UserMapper userMapper;
    private LogMapper logMapper;
    private ActionLogDTO actionLogDTO;
    private LocalDateTime requestDate;
    private String userTyCd;
    private UserTokenMapper userTokenMapper;

    private AccountCredentials creds;

    String key = "63EC82B7267BDA34722A92B63C5305AE"; //(32/16자리)
    String iv = "722A92B63C5305AE";//(16자리)
    AES256Util aes;

    public JWTLoginFilter(String url, AuthenticationManager authManager, UserMapper userMapper, LogMapper logMapper, UserJpaRepository userJpaRepository, UserTokenMapper userTokenMapper) throws UnsupportedEncodingException {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
        this.userMapper = userMapper;
        this.logMapper = logMapper;
        this.userJpaPository = userJpaRepository;
        this.actionLogDTO = new ActionLogDTO();
        this.userTokenMapper = userTokenMapper;
        requestDate = LocalDateTime.now();
        carmanagerAesConfig = AppContextManager.getBean(CarmanagerAesConfig.class);
        aes = new AES256Util(this.key, this.iv);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException, IOException, ServletException {
        //creds = new ObjectMapper().readValue(req.getInputStream(), AccountCredentials.class);
        creds = new AccountCredentials();
        creds.setUsername(req.getParameter("username"));
        creds.setPassword(req.getParameter("password"));
        creds.setChannel(req.getParameter("channel"));
        creds.setRememberMe(!StringUtil.isEmpty(req.getParameter("_a")) && req.getParameter("_a").equalsIgnoreCase("1"));
        userId = creds.getUsername();
        userTyCd = creds.getUserTyCd();
        actionLogDTO = getLogObject(req, creds);
        actionLogDTO.setReqArgs(new ObjectMapper().writeValueAsString(creds));
        String userPassword = null;
        try {
            userPassword = aes.encrypt(creds.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(), userPassword, Collections.emptyList()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException {
        userMapper.updateLastLoginDt(auth.getName());
        try {
            TokenAuthenticationService.addAuthentication(actionLogDTO, req, res, auth, logMapper, userTyCd, userTokenMapper, aes.encrypt(creds.getPassword()), creds.isRememberMe());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ApiResponse apiResponse;

        if (failed instanceof BadCredentialsException) {
            apiResponse = ApiResponse.badCredential();

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 잘못된 요청.
        } else if (failed instanceof UsernameNotFoundException) {
            apiResponse = ApiResponse.usernameNotFound();

            response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 성공.
        } else {
            apiResponse = ApiResponse.error();

            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 서버 오류.
        }

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        response.setCharacterEncoding(Constant.UTF_8);


        try {
        	//로그인 실패 ㄹ그
        	ActionLogDTO loginInfo = getLogObject(request, creds);
        	loginInfo.setResultCode(String.valueOf(response.getStatus()));
        	loginInfo.setReqArgs(new ObjectMapper().writeValueAsString(request.getInputStream()));
            loginInfo.setLoginSucYn("N");
            loginInfo.setUserId(creds.getUsername());

        	logMapper.insertLoginLog(loginInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}

        response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
    }

    private ActionLogDTO getLogObject(HttpServletRequest request, AccountCredentials creds) {
        ClientInfo clientInfo = new ClientInfo(request);
        ActionLogDTO actionLogDTO = new ActionLogDTO();
        actionLogDTO.setActionUrl(clientInfo.getPathInfo());
        actionLogDTO.setBrwsrInfo(clientInfo.getClientBrowser());
        actionLogDTO.setOsClass(clientInfo.getClientOS());
        actionLogDTO.setPid(Constant.PID);
        actionLogDTO.setChannel(creds.getChannel());
        actionLogDTO.setIp(clientInfo.getClientIpAddr());
        actionLogDTO.setReqDt(requestDate);
        actionLogDTO.setActionMethod(clientInfo.getRequestMethod());

        return actionLogDTO;
    }
}
