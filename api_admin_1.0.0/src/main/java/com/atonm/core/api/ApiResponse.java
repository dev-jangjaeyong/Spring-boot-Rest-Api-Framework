package com.atonm.core.api;

import com.atonm.core.interceptor.BufferedResponseWrapper;
import com.atonm.kblease.api.utils.StringUtil;
import com.atonm.core.dto.AuthorityErrorDTO;
import com.atonm.core.dto.BindErrorsDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author jang jea young
 * @since 2018-08-09.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private ApiStatus apiStatus;
    private String apiCode;
    private List resultList;
    private Object resultObject;
    private Integer resultCount;
    private String tokenId;
    private String message;
    private String requestTime;
    private String responseTime;
    private String difftime;
    private String sessionId;
    private String uid;

    public static ApiResponse ok(List resultList) {
        return ApiResponse.of(ApiStatus.SUCCESS, resultList);
    }

    public static ApiResponse ok(Object resultObject) {
        return ApiResponse.of(ApiStatus.SUCCESS, resultObject);
    }

    public static ApiResponse login(Object resultObject, String uid) {
        return ApiResponse.login(ApiStatus.SUCCESS, resultObject, uid);
    }

    public static ApiResponse ok(Integer resultCount) {
        return ApiResponse.of(ApiStatus.SUCCESS, resultCount);
    }

    public static ApiResponse ok(Integer resultCount, String tokenId) {
        return ApiResponse.of(ApiStatus.SUCCESS, resultCount, tokenId);
    }

    public static ApiResponse ok(List resultList, Integer resultCount) {
        return ApiResponse.of(ApiStatus.SUCCESS, resultList, resultCount);
    }

    public static ApiResponse duplicate() {
        return ApiResponse.of(ApiStatus.DUPLICATE);
    }

    public static ApiResponse error() {
        return ApiResponse.of(ApiStatus.SERVER_ERROR, "요청 중 오류가 발생했습니다.");
    }

    public static ApiResponse expiredToken(Object resultObject) {
        return ApiResponse.of(ApiStatus.EXPIRED_TOKEN, resultObject, "만료된 인증정보입니다.");
    }

    public static ApiResponse expiredToken() {
        return ApiResponse.of(ApiStatus.EXPIRED_TOKEN, "만료된 인증정보입니다.");
    }

    /*public static ApiResponse accessDenied() {
        return ApiResponse.of(ApiStatus.ACCESS_DENIED, "접근이 거부되었습니다.");
    }*/

    public static ApiResponse unauthorized() {
        return ApiResponse.of(ApiStatus.UNAUTHORIZED, "인증정보가 존재하지 않습니다.");
    }

    public static ApiResponse expireSession() {
        return ApiResponse.of(ApiStatus.EXPIRED_SESSION, "만료된 세션입니다.");
    }

    public static ApiResponse menuNnauthorized() {
        return ApiResponse.of(ApiStatus.MENU_UNAUTHORIZED, "메뉴접근 권한이 없습니다.");
    }

    public static ApiResponse badCredential() {
        return ApiResponse.of(ApiStatus.BAD_CREDENTIAL, "잘못된 인증정보입니다.");
    }

    public static ApiResponse usernameNotFound() {
        return ApiResponse.of(ApiStatus.USER_NOT_FOUND, "존재하지 않는 사용자입니다.");
    }

    public static ApiResponse badRequest() {
        return ApiResponse.of(ApiStatus.BAD_REQUEST, "잘못된 요청입니다.");
    }

    public static ApiResponse badRequest(BindErrorsDTO errors) {
        return ApiResponse.of(ApiStatus.BAD_REQUEST, errors, "잘못된 요청입니다.");
    }

    public static ApiResponse badRequest(String message) {
        return ApiResponse.of(ApiStatus.BAD_REQUEST, message);
    }

    public static ApiResponse badRequest(List resultList) {
        return ApiResponse.of(ApiStatus.BAD_REQUEST, resultList, "잘못된 요청입니다.");
    }

    public static ApiResponse taskException(String... message) {
    	String errMessage = null;
    	if(message == null) {
    		errMessage = "요청 처리 중 오류가 발생했습니다.";
    	}else {
    		errMessage = message[0];
    	}
        return ApiResponse.of(ApiStatus.TASK_EXCEPTION, errMessage);
    }

    public static ApiResponse taskException(ApiStatus status, String message) {
    	return ApiResponse.of(status, message);
    }

    public static ApiResponse missingRequiredParameter(List resultList) {
        return ApiResponse.of(ApiStatus.MISSING_REQUIRED_PARAMETER, resultList, "필수 파라미터 누락.");
    }

    public static ApiResponse missingRequiredParameter(String message) {
        return ApiResponse.of(ApiStatus.MISSING_REQUIRED_PARAMETER,message);
    }

    public static ApiResponse incorrectRangeOfArgument(List resultList) {
        return ApiResponse.of(ApiStatus.INCORRECT_RANGE_OF_ARGUMENT, resultList, "잘못된 범위의 파라미터");
    }

    public static ApiResponse argumentOfErroneousDataTypes(List resultList) {
        return ApiResponse.of(ApiStatus.ARGUMENT_OF_ERRONEOUS_DATA_TYPES, resultList, "잘못된 자료형의 파라미터");
    }

    public static ApiResponse notExistsApiAuthority(AuthorityErrorDTO resultObject) {
        return ApiResponse.of(ApiStatus.API_REQUEST_PERMISSION_DOES_NOT_EXIST, resultObject, "API 요청 권한이 존재하지 않습니다.");
    }

    public static ApiResponse notExistsMenuAuthority(AuthorityErrorDTO resultObject) {
        return ApiResponse.of(ApiStatus.MENU_REQUEST_PERMISSION_DOES_NOT_EXIST, resultObject, "메뉴 요청 권한이 존재하지 않습니다.");
    }

    public static ApiResponse notFoundResponse(String message) {
        return ApiResponse.of(ApiStatus.NOT_FOUND_RESPONSE, !StringUtil.isEmpty(message) ? message : "해당 결과가 존재하지 않습니다.");
    }

    private static ApiResponse of(ApiStatus apiStatus) {
        Map<String, Object> _tokenStatus = getTokenId(apiStatus);
        ApiStatus _apiStatus = (ApiStatus)_tokenStatus.get("apiStatus");
        return ApiResponse.builder()
                .apiStatus(_apiStatus)
                .apiCode(_apiStatus.getValue().get(apiStatus.toString()))
                .tokenId(String.valueOf(_tokenStatus.get("tokenId")))
                .sessionId(String.valueOf(_tokenStatus.get("sessionId")))
                .build();
    }

    private static ApiResponse of(ApiStatus apiStatus, String message) {
        Map<String, Object> _tokenStatus = getTokenId(apiStatus);
        ApiStatus _apiStatus = (ApiStatus)_tokenStatus.get("apiStatus");
        return ApiResponse.builder()
                .apiStatus(_apiStatus)
                .apiCode(_apiStatus.getValue().get(_apiStatus.toString()))
                .message(message == null ? _apiStatus.getValue().get(apiStatus.toString()) : message)
                .tokenId(String.valueOf(_tokenStatus.get("tokenId")))
                .sessionId(String.valueOf(_tokenStatus.get("sessionId")))
                .build();
    }
    private static ApiResponse of(ApiStatus apiStatus, List resultList, String message) {
        Map<String, Object> _tokenStatus = getTokenId(apiStatus);
        ApiStatus _apiStatus = (ApiStatus)_tokenStatus.get("apiStatus");
        return ApiResponse.builder()
                .apiStatus(_apiStatus)
                .resultList(resultList)
                .apiCode(_apiStatus.getValue().get(_apiStatus.toString()))
                .tokenId(String.valueOf(_tokenStatus.get("tokenId")))
                .sessionId(String.valueOf(_tokenStatus.get("sessionId")))
                .message(message)
                .build();
    }

    private static ApiResponse of(ApiStatus apiStatus, List resultList, Integer resultCount) {
        Map<String, Object> _tokenStatus = getTokenId(apiStatus);
        ApiStatus _apiStatus = (ApiStatus)_tokenStatus.get("apiStatus");
        return ApiResponse.builder()
                .apiStatus(_apiStatus)
                .resultList(resultList)
                .resultCount(resultCount)
                .apiCode(_apiStatus.getValue().get(_apiStatus.toString()))
                .tokenId(String.valueOf(_tokenStatus.get("tokenId")))
                .sessionId(String.valueOf(_tokenStatus.get("sessionId")))
                .build();
    }

    private static ApiResponse of(ApiStatus apiStatus, List resultList) {
        Map<String, Object> _tokenStatus = getTokenId(apiStatus);
        ApiStatus _apiStatus = (ApiStatus)_tokenStatus.get("apiStatus");
        return ApiResponse.builder()
                .apiStatus(_apiStatus)
                .resultList(resultList)
                .resultCount(resultList.size())
                .apiCode(_apiStatus.getValue().get(_apiStatus.toString()))
                .tokenId(String.valueOf(_tokenStatus.get("tokenId")))
                .sessionId(String.valueOf(_tokenStatus.get("sessionId")))
                .build();
    }

    private static ApiResponse of(ApiStatus apiStatus, Object resultObject) {
        Map<String, Object> _tokenStatus = getTokenId(apiStatus);
        ApiStatus _apiStatus = (ApiStatus)_tokenStatus.get("apiStatus");
        return ApiResponse.builder()
                .apiStatus(_apiStatus)
                .resultObject(resultObject)
                .resultCount(Objects.nonNull(resultObject) ? 1 : 0)
                .apiCode(_apiStatus.getValue().get(_apiStatus.toString()))
                .tokenId(String.valueOf(_tokenStatus.get("tokenId")))
                .sessionId(String.valueOf(_tokenStatus.get("sessionId")))
                .build();
    }

    private static ApiResponse login(ApiStatus apiStatus, Object resultObject, String uid) {
        Map<String, Object> _tokenStatus = getTokenId(apiStatus);
        ApiStatus _apiStatus = (ApiStatus)_tokenStatus.get("apiStatus");
        return ApiResponse.builder()
                .apiStatus(_apiStatus)
                .resultObject(resultObject)
                .resultCount(Objects.nonNull(resultObject) ? 1 : 0)
                .apiCode(_apiStatus.getValue().get(_apiStatus.toString()))
                .tokenId(String.valueOf(_tokenStatus.get("tokenId")))
                .sessionId(String.valueOf(_tokenStatus.get("sessionId")))
                .uid(uid)
                .build();
    }

    private static ApiResponse of(ApiStatus apiStatus, Object resultObject, String message) {
        Map<String, Object> _tokenStatus = getTokenId(apiStatus);
        ApiStatus _apiStatus = (ApiStatus)_tokenStatus.get("apiStatus");
        return ApiResponse.builder()
                .apiStatus(_apiStatus)
                .resultObject(resultObject)
                .message(message)
                .resultCount(Objects.nonNull(resultObject) ? 1 : 0)
                .apiCode(_apiStatus.getValue().get(_apiStatus.toString()))
                .tokenId(String.valueOf(_tokenStatus.get("tokenId")))
                .sessionId(String.valueOf(_tokenStatus.get("sessionId")))
                .build();
    }

    private static ApiResponse of(ApiStatus apiStatus, Integer resultCount) {
        Map<String, Object> _tokenStatus = getTokenId(apiStatus);
        ApiStatus _apiStatus = (ApiStatus)_tokenStatus.get("apiStatus");
        return ApiResponse.builder()
                .apiStatus(_apiStatus)
                .resultCount(resultCount)
                .apiCode(_apiStatus.getValue().get(_apiStatus.toString()))
                .tokenId(String.valueOf(_tokenStatus.get("tokenId")))
                .sessionId(String.valueOf(_tokenStatus.get("sessionId")))
                .build();
    }

    private static Map<String, Object> getTokenId(ApiStatus apiStatus) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String _oldToken = request.getHeader("authorization");
        String _token = response.getHeader("authorization");

        System.out.println("_oldToken : " + _oldToken);
        System.out.println("_newToken : " + _token);

        if(!StringUtil.isEmpty(_oldToken) && !_oldToken.equalsIgnoreCase(_token)) {
            //apiStatus = ApiStatus.EXPIRED_TOKEN;
            apiStatus = ApiStatus.SUCCESS;
        }
        Map<String, Object> _ret = new HashMap<>();
        _ret.put("apiStatus", apiStatus);
        _ret.put("tokenId", _token);
        _ret.put("sessionId", request.getSession(false) != null ? request.getSession(false).getId() : "");

        return _ret;
    }
}
