package com.atonm.kblease.api.config.security;

import com.atonm.core.api.ApiResponse;
import com.atonm.core.common.constant.Constant;
import com.atonm.kblease.api.common.mapper.UserTokenMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author jang jea young
 * @since 2018-07-30
 **/
public class JWTAuthenticationFilter extends GenericFilterBean {
    private final UserTokenMapper userTokenMapper;

    public JWTAuthenticationFilter(UserTokenMapper userTokenMapper) {
        this.userTokenMapper = userTokenMapper;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            Authentication authentication = TokenAuthenticationService.getAuthentication((HttpServletRequest) request, (HttpServletResponse) response, userTokenMapper);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            ApiResponse apiResponse = ApiResponse.expiredToken();
            jwtAuthException((HttpServletResponse) response, apiResponse);
        } catch (MalformedJwtException e) {
            ApiResponse apiResponse = ApiResponse.unauthorized();
            jwtAuthException((HttpServletResponse) response, apiResponse);
        }
    }

    private void jwtAuthException(HttpServletResponse response, ApiResponse apiResponse) throws IOException {
        HttpServletResponse httpServletResponse = response;
        httpServletResponse.setCharacterEncoding(Constant.UTF_8);
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
    }
}
