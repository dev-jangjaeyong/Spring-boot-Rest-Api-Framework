package com.atonm.kblease.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author jang jae young
 * @since 2019-06-28 
 * ToBe System에서 공통 API 서버로서의 기능을 수행함에 따라 
 * API 오류상태(타 API 서버에 연결 및 처리가 불가능한 상태일 경우)
 */
@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
public class ServiceException extends Exception{
	public ServiceException(String message) {
		super(message);
	}
}
