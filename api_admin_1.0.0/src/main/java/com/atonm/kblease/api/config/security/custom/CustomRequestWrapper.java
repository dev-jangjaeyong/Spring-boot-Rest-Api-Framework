package com.atonm.kblease.api.config.security.custom;

import com.atonm.core.common.constant.Constant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author jang jea young
 * @since 2018-08-20
 */
public class CustomRequestWrapper extends HttpServletRequestWrapper {
    private String accessToken;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request The request to wrap
     * @throws IllegalArgumentException if the request is null
     */
    public CustomRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    public CustomRequestWrapper(HttpServletRequest request, String accessToken) {
        this(request);
        this.accessToken = accessToken;
    }

    @Override
    public String getHeader(String name) {
        if (name.equals(Constant.ACCESS_TOKEN_HEADER_NAME)) {
            return this.accessToken;
        } else {
            return super.getHeader(name);
        }
    }
}
