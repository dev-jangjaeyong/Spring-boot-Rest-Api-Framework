package com.atonm.kblease.api.config.converter;

import com.atonm.kblease.api.ApiApplication;

import com.atonm.kblease.api.commapi.dto.CommonCode;
import com.atonm.kblease.api.commapi.dto.CommonCodeDTO;
import org.modelmapper.PropertyMap;

/**
 * @author jang jea young
 * @since 2018-08-09
 * @see ApiApplication
 */
public class CommonCodeMap {
    public static PropertyMap<CommonCode, CommonCodeDTO> toDTO() {
        return new PropertyMap<CommonCode, CommonCodeDTO>() {
            @Override
            protected void configure() {
                map().setDscrp(source.getCodeName()); // Front, jsTree 작성용.
                skip().setParent(null);
            }
        };
    }
}
