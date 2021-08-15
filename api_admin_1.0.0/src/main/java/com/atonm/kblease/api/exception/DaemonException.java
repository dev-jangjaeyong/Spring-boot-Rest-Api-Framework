package com.atonm.kblease.api.exception;

import com.atonm.core.api.ApiStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author jang jae young
 * @since 2019-06-28 
 * Daemon 서비스에서 활용
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND)
public class DaemonException extends Exception{
	private ApiStatus status = ApiStatus.NOT_FOUND_AGENT;
	
	public DaemonException(String message) {
		super(message);
	}
	
	public DaemonException(ApiStatus status, String message) {
		super(message);
		this.status = status;
	}
	
	public ApiStatus getApiStatus() {
		return this.status;
	}
}
