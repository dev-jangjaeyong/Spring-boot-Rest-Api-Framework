package com.atonm.kblease.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author jang jae young
 * @since 2019-06-28 
 * ToBe System에서 공통 API 서버로서의 기능을 수행함에 따라 
 * API 오류에 대한 Exception 정의
 */
@ResponseStatus(value=HttpStatus.BAD_REQUEST)
public class APIException extends Exception{
	public APIException(String message) {
		super(message);
	}
}
