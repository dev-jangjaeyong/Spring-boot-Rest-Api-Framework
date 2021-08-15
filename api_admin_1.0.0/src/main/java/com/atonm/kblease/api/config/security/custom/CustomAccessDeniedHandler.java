package com.atonm.kblease.api.config.security.custom;

import com.atonm.core.api.ApiResponse;
import com.atonm.core.common.constant.Constant;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author jang jea young
 * @since 2018-08-02
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exc) throws IOException, ServletException {
        ApiResponse apiResponse = ApiResponse.unauthorized();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 금지됨, 서버가 요청을 거부하고 있다. 예를 들자면, 사용자가 리소스에 대한 필요 권한을 갖고 있지 않다. (401은 인증 실패, 403은 인가 실패라고 볼 수 있음)

        response.setCharacterEncoding(Constant.UTF_8);

        response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
    }
}
