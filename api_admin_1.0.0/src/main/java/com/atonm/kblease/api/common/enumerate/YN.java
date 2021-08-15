package com.atonm.kblease.api.common.enumerate;

/**
 * @author jang jea young
 * @since 2021-05-08
 */
public enum YN {
    Y ("Y"),
    N ("N");

    private String val;

    YN(String source) {
        this.val = source;
    }

    public String val() {
        return this.val;
    }
}
