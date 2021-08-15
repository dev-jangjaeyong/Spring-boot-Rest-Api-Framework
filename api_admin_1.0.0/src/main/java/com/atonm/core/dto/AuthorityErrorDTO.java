package com.atonm.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jang jea young
 * @since 2018-11-05
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorityErrorDTO {
    private String uri;
    private String method;
}
