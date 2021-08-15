package com.atonm.kblease.api.common.exception;

/**
 * @author jang jea young
 * @since 2018-09-06
 */
public class UserInfoNotFoundException extends RuntimeException  {
    public UserInfoNotFoundException(String message) {
        super(message);
    }
}
