package com.atonm.kblease.api.common.base;

import org.springframework.http.ResponseEntity;
import com.atonm.core.api.ApiResponse;
import com.atonm.core.dto.BindErrorsDTO;

import java.util.Collections;


/**
 * @author jang jea young
 * @since 2018-08-09
 */
public class BaseRestController {
    protected ResponseEntity<ApiResponse> ok() {
        return ResponseEntity.ok(ApiResponse.ok(1));
    }

    protected ResponseEntity<ApiResponse> ok(ApiResponse apiResponse) {
        return ResponseEntity.ok(apiResponse);
    }

    protected ResponseEntity<ApiResponse> badRequest(BindErrorsDTO bindErrors) {
        return ResponseEntity.badRequest().body(ApiResponse.badRequest(Collections.singletonList(bindErrors)));
    }

    protected ResponseEntity<ApiResponse> badRequest(ApiResponse apiResponse) {
        return ResponseEntity.badRequest().body(apiResponse);
    }

    protected ResponseEntity<ApiResponse> badRequest() {
        return ResponseEntity.badRequest().body(ApiResponse.badRequest());
    }
}
