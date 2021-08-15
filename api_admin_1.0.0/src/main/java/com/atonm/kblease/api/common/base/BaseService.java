package com.atonm.kblease.api.common.base;

import com.atonm.core.api.ApiStatus;
import com.atonm.kblease.api.utils.ModelMapperUtils;
import org.modelmapper.MappingException;
import com.atonm.core.api.ApiResponse;

import java.util.List;

/**
 * @author jang jea young
 * @since 2018-08-09
 */
public class BaseService {

    protected <T> T map(Object source, Class<T> destinationClass) throws MappingException {
        return ModelMapperUtils.map(source, destinationClass);
    }

    protected <T> List<T> map(Iterable<?> iterableSources, Class<T> destinationClass) throws MappingException {
        return ModelMapperUtils.mapList(iterableSources, destinationClass);
    }

    protected  ApiResponse ok() {
        return ApiResponse.ok(1);
    }

    protected ApiResponse ok(List resultList) {
        return ApiResponse.ok(resultList);
    }

    protected ApiResponse ok(Object resultObject) {
        return ApiResponse.ok(resultObject);
    }

    protected ApiResponse ok(Integer resultCount) {
        return ApiResponse.ok(resultCount);
    }

    protected ApiResponse ok(List resultList, Integer resultCount) {
        return ApiResponse.ok(resultList, resultCount);
    }

    protected ApiResponse error() { return ApiResponse.taskException(ApiStatus.SERVER_ERROR.getValue().get(ApiStatus.SERVER_ERROR.toString())); }
    protected ApiResponse badRequest() { return ApiResponse.taskException(ApiStatus.BAD_REQUEST.getValue().get(ApiStatus.BAD_REQUEST.toString())); }

    protected ApiResponse failCause(String message) {
    	return ApiResponse.taskException(message);
    }

    protected ApiResponse failCause(ApiStatus status, String message) {
    	return ApiResponse.taskException(status, message);
    }

    protected ApiResponse notFoundResponse(String message) {
        return ApiResponse.notFoundResponse(message);
    }

    protected ApiResponse missingRequiredParameter(String message) {
        return ApiResponse.missingRequiredParameter(message);
    }

    protected ApiResponse missingRequiredParameter(List resultList) {
        return ApiResponse.missingRequiredParameter(resultList);
    }

    protected  ApiResponse logoutok(String tokenId) {
        return ApiResponse.ok(1, tokenId);
    }
}
