package com.atonm.kblease.api.config.security;

import lombok.Getter;
import lombok.Setter;

/**
 * @author jang jea young
 * @since 2018-07-30
 */
@Getter
@Setter
class AccountCredentials {
    private String username;
    private String password;
    private boolean rememberMe;
    private String channel;
    private String userTyCd;
}
