package com.atonm.kblease.api.commapi.dto;

import lombok.Data;

/**
 * @author jang jea young
 * @since 2018-08-09
 */
@Data
public class BaseDTO {
    private String pid;
    private String channel;

//    @JsonFormat(pattern = Format.LOCAL_DATE_TIME)
//    private LocalDateTime createDt;

    private String createDt;
    private Long createUid;

//    @JsonFormat(pattern = Format.LOCAL_DATE_TIME)
//    private LocalDateTime updateDt;

    private String updateDt;
    private Long updateUid;

    private Long totalCount;
    private String currentUserId;
    private Integer currentPageNo = 1;
    private Integer rowsPerPage = 5;

    private Integer startPage = ((currentPageNo - 1) / rowsPerPage) * rowsPerPage + 1;
    private Integer endPage = startPage + rowsPerPage - 1;

    private Long startNumber = 0L;
    private Long endNumber = startNumber + rowsPerPage*currentPageNo;

    public Integer getOffset() {
        return (currentPageNo * rowsPerPage) - rowsPerPage;
    }
}
