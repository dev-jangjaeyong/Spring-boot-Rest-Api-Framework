package com.atonm.kblease.api.config.converter;

import com.atonm.kblease.api.common.enumerate.YN;
import com.atonm.kblease.api.config.web.WebMvcConfig;
import org.springframework.core.convert.converter.Converter;

/**
 * @author jang jea young
 * @since 2018-08-09
 * @see WebMvcConfig
 */
public class YNConverter implements Converter<String, YN> {
    @Override
    public YN convert(String source) {
        return YN.valueOf(source);
    }
}
