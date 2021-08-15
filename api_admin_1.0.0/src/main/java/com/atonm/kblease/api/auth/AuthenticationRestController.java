package com.atonm.kblease.api.auth;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.atonm.kblease.api.utils.SecurityUtils;
import com.atonm.core.api.ApiResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author jang jea young
 * @since 2018-08-16
 */
@RestController
public class AuthenticationRestController {

    /**
     * 로그인 여부 확인.
     */
    @RequestMapping("/whether-login")
    @ApiOperation(value = "로그인 여부 확인")
    public ResponseEntity<ApiResponse> whetherLogin(HttpServletRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(SecurityUtils.whetherLogin()));
    }
}
