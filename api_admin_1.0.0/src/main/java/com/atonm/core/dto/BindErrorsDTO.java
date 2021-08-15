package com.atonm.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jang jea young
 * @since 2018-08-10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BindErrorsDTO {
    private String field;
    private String message;
    private Object rejectedValue;

    public static com.atonm.core.dto.BindErrorsDTO of(String field, String message, Object rejectedValue) {
        return new com.atonm.core.dto.BindErrorsDTO(field, message, rejectedValue);
    }

    public static BindErrorsDTO of(String field, String message) {
        return new BindErrorsDTO(field, message, null);
    }
}
