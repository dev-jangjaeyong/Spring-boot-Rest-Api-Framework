package com.atonm.kblease.api.controller;

import com.atonm.core.api.ApiResponse;
import com.atonm.kblease.api.common.base.BaseRestController;
import com.atonm.kblease.api.service.KbUserService;
import com.atonm.kblease.api.utils.SecurityUtils;
import com.atonm.kblease.api.utils.StringUtil;
import com.atonm.kblease.api.utils.annotation.HttpGetMapping;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import static com.atonm.core.common.constant.Constant.REFRESH_TOKEN_UID_HEADER_NAME;

/**
 * @author jang jea young
 * @since 2018-08-09
 */
@RestController
@RequiredArgsConstructor
public class KbUserRestController extends BaseRestController {

    private final KbUserService kbUserService;

    @HttpGetMapping("/user/logout-proc")
    @ApiOperation(value = "로그아웃 시 back process 정의")
    public ResponseEntity<ApiResponse> logoutUserLogin(HttpServletRequest request) {
        return ok(kbUserService.logoutProc(request));
    }

    @HttpGetMapping("/user/role")
    @ApiOperation(value = "로그인 후 ROLE 조회")
    public ResponseEntity<ApiResponse> userRole(HttpServletRequest request) {
        return ok(kbUserService.loginRole(request));
    }
}
