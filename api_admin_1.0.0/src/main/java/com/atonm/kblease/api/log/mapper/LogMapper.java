package com.atonm.kblease.api.log.mapper;

import com.atonm.kblease.api.config.datasource.annotation.Master;
import com.atonm.kblease.api.log.dto.AccessLogDTO;
import com.atonm.kblease.api.log.dto.ActionLogDTO;
import com.atonm.kblease.api.log.dto.LeaseCallLogDTO;

/**
 * @author jang jea young
 * @since 2021-05-08
 */
@Master
public interface LogMapper {
	int insertActionLog(ActionLogDTO actionLog);
	int insertAccessLog(AccessLogDTO accessLog);
	void insertLeaseCallLog(LeaseCallLogDTO leaseCallLogDTO);

	void insertLoginLog(ActionLogDTO logObject);

}
