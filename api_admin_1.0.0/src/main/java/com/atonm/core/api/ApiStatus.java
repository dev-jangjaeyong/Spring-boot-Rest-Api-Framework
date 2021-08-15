package com.atonm.core.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

/**
 * @author jang jea young
 * @since 2018-08-07.
 */
public enum ApiStatus {
    @JsonProperty("SUCCESS")
    SUCCESS(new HashMap<String, String>() {{put("SUCCESS","S001");}}),
    @JsonProperty("EXPIRED_TOKEN")
    EXPIRED_TOKEN(new HashMap<String, String>() {{put("EXPIRED_TOKEN","E011");}}),
    @JsonProperty("WONBU_NETWORK_ERROR")
    WONBU_NETWORK_ERROR(new HashMap<String, String>() {{put("WONBU_NETWORK_ERROR","E100");}}),
    @JsonProperty("WONBU_LOGIC_ERROR")
    WONBU_LOGIC_ERROR(new HashMap<String, String>() {{put("WONBU_LOGIC_ERROR","E101");}}),
    @JsonProperty("WONBU_TEMPORARY_ERROR")
    WONBU_TEMPORARY_ERROR(new HashMap<String, String>() {{put("WONBU_TEMPORARY_ERROR","E105");}}),
    @JsonProperty("CARZEN_UNKNOWN_CAR")
    CARZEN_UNKNOWN_CAR(new HashMap<String, String>() {{put("CARZEN_UNKNOWN_CAR","E200");}}),
    @JsonProperty("CARZEN_CARFRAME_FORM_ERROR")
    CARZEN_CARFRAME_FORM_ERROR(new HashMap<String, String>() {{put("CARZEN_CARFRAME_FORM_ERROR","E400");}}),
    @JsonProperty("CARZEN_UNAUTHORIZED")
    CARZEN_UNAUTHORIZED(new HashMap<String, String>() {{put("CARZEN_UNAUTHORIZED","E403");}}),
    @JsonProperty("CAR_MODEL_NO_MAPPING_ERROR")
    CAR_MODEL_NO_MAPPING_ERROR(new HashMap<String, String>() {{put("CAR_MODEL_NO_MAPPING_ERROR","E404");}}),
    @JsonProperty("CARZEN_UNKNOWN_ERROR")
    CARZEN_UNKNOWN_ERROR(new HashMap<String, String>() {{put("CARZEN_UNKNOWN_ERROR","E999");}}),
    @JsonProperty("EXPIRED_SESSION")
    EXPIRED_SESSION(new HashMap<String, String>() {{put("EXPIRED_SESSION","E016");}}),
    @JsonProperty("UNAUTHORIZED")
    UNAUTHORIZED(new HashMap<String, String>() {{put("UNAUTHORIZED","E001");}}),
    @JsonProperty("BIDDING_UNAUTHORIZED")
    BIDDING_UNAUTHORIZED(new HashMap<String, String>() {{put("BIDDING_UNAUTHORIZED","E003");}}),
    @JsonProperty("BAD_CREDENTIAL")
    BAD_CREDENTIAL(new HashMap<String, String>() {{put("BAD_CREDENTIAL","E010");}}),
    @JsonProperty("USER_NOT_FOUND")
    USER_NOT_FOUND(new HashMap<String, String>() {{put("USER_NOT_FOUND","E012");}}),
    @JsonProperty("DUPLICATE")
    DUPLICATE(new HashMap<String, String>() {{put("DUPLICATE","E014");}}),
    @JsonProperty("TASK_EXCEPTION")
    TASK_EXCEPTION(new HashMap<String, String>() {{put("TASK_EXCEPTION","E017");}}),
    @JsonProperty("MENU_UNAUTHORIZED")
    MENU_UNAUTHORIZED(new HashMap<String, String>() {{put("MENU_UNAUTHORIZED","E018");}}),
    @JsonProperty("PASSCAR_REGISTING_CAR")
    PASSCAR_REGISTING_CAR(new HashMap<String, String>() {{put("PASSCAR_REGISTING_CAR","E019");}}),
    @JsonProperty("FILE_UPLOAD_SUCCESS")
    FILE_UPLOAD_SUCCESS(new HashMap<String, String>() {{put("SUCCESS","S010");}}),
    @JsonProperty("FILE_REMOVE_SUCCESS")
    FILE_REMOVE_SUCCESS(new HashMap<String, String>() {{put("SUCCESS","S011");}}),
    @JsonProperty("NEED_TO_PAY")
    NEED_TO_PAY(new HashMap<String, String>() {{put("NEED_TO_PAY","E002");}}),
    @JsonProperty("NOT_FOUND")
    NOT_FOUND(new HashMap<String, String>() {{put("NOT_FOUND","E004");}}),
    @JsonProperty("NOT_FOUND_AGENT")
    NOT_FOUND_AGENT(new HashMap<String, String>() {{put("NOT_FOUND_AGENT","E090");}}),
    @JsonProperty("NOT_MAPPING_REST")
    NOT_MAPPING_REST(new HashMap<String, String>() {{put("NOT_MAPPING_REST","E005");}}),
    @JsonProperty("TIMEOUT")
    TIMEOUT(new HashMap<String, String>() {{put("TIMEOUT","E006");}}),
    @JsonProperty("NOT_FOUND_CONTENT_LENGTH")
    NOT_FOUND_CONTENT_LENGTH(new HashMap<String, String>() {{put("NOT_FOUND_CONTENT_LENGTH","E007");}}),
    @JsonProperty("MANY_REQUEST")
    MANY_REQUEST(new HashMap<String, String>() {{put("MANY_REQUEST","E008");}}),
    @JsonProperty("MENU_REQUEST_PERMISSION_DOES_NOT_EXIST")
    MENU_REQUEST_PERMISSION_DOES_NOT_EXIST(new HashMap<String, String>() {{put("MENU_REQUEST_PERMISSION_DOES_NOT_EXIST","E013");}}),
    @JsonProperty("API_REQUEST_PERMISSION_DOES_NOT_EXIST")
    API_REQUEST_PERMISSION_DOES_NOT_EXIST(new HashMap<String, String>() {{put("API_REQUEST_PERMISSION_DOES_NOT_EXIST","E014");}}),
    @JsonProperty("BAD_REQUEST")
    BAD_REQUEST(new HashMap<String, String>() {{put("BAD_REQUEST","E015");}}),
    @JsonProperty("MISSING_REQUIRED_PARAMETER")
    MISSING_REQUIRED_PARAMETER(new HashMap<String, String>() {{put("MISSING_REQUIRED_PARAMETER","E021");}}),
    @JsonProperty("INCORRECT_RANGE_OF_ARGUMENT")
    INCORRECT_RANGE_OF_ARGUMENT(new HashMap<String, String>() {{put("INCORRECT_RANGE_OF_ARGUMENT","E022");}}),
    @JsonProperty("ARGUMENT_OF_ERRONEOUS_DATA_TYPES")
    ARGUMENT_OF_ERRONEOUS_DATA_TYPES(new HashMap<String, String>() {{put("ARGUMENT_OF_ERRONEOUS_DATA_TYPES","E023");}}),
    @JsonProperty("NOT_FOUND_RESPONSE")
    NOT_FOUND_RESPONSE(new HashMap<String, String>() {{put("NOT_FOUND_RESPONSE","E024");}}),
    @JsonProperty("MAX_SIZE_EXCESS")
    MAX_SIZE_EXCESS(new HashMap<String, String>() {{put("MAX_SIZE_EXCESS","E031");}}),
    @JsonProperty("NO_FILE_WRITE_PERMISSION")
    NO_FILE_WRITE_PERMISSION(new HashMap<String, String>() {{put("NO_FILE_WRITE_PERMISSION","E032");}}),
    @JsonProperty("FILE_SIZE_ZERO")
    FILE_SIZE_ZERO(new HashMap<String, String>() {{put("FILE_SIZE_ZERO","E033");}}),
    @JsonProperty("UNSUPPORTED_FILE_TYPE")
    UNSUPPORTED_FILE_TYPE(new HashMap<String, String>() {{put("UNSUPPORTED_FILE_TYPE","E034");}}),
    @JsonProperty("ONLY_SOME_FILES_HAVE_BEEN_UPLOADED")
    ONLY_SOME_FILES_HAVE_BEEN_UPLOADED(new HashMap<String, String>() {{put("ONLY_SOME_FILES_HAVE_BEEN_UPLOADED","E035");}}),
    @JsonProperty("SERVER_ERROR")
    SERVER_ERROR(new HashMap<String, String>() {{put("SERVER_ERROR","E500");}}),
    @JsonProperty("SERVER_DOES_NOT_RECOGNIZE_REQUEST_METHOD")
    SERVER_DOES_NOT_RECOGNIZE_REQUEST_METHOD(new HashMap<String, String>() {{put("SERVER_DOES_NOT_RECOGNIZE_REQUEST_METHOD","E501");}}),
    @JsonProperty("BAD_GATEWAY")
    BAD_GATEWAY(new HashMap<String, String>() {{put("BAD_GATEWAY","E502");}}),
    @JsonProperty("SERVICE_UNAVAILABLE")
    SERVICE_UNAVAILABLE(new HashMap<String, String>() {{put("SERVICE_UNAVAILABLE","E503");}}),
    @JsonProperty("GATEWAY_TIMEOUT")
    GATEWAY_TIMEOUT(new HashMap<String, String>() {{put("GATEWAY_TIMEOUT","E504");}}),
    @JsonProperty("HTTP_VERSION_NOT_SUPPORTED")
    HTTP_VERSION_NOT_SUPPORTED(new HashMap<String, String>() {{put("HTTP_VERSION_NOT_SUPPORTED","E505");}}),
    @JsonProperty("LOOP_DETECTED")
    LOOP_DETECTED(new HashMap<String, String>() {{put("LOOP_DETECTED","E508");}}),
    @JsonProperty("BANDWIDTH_LIMIT_EXCEEDED")
    BANDWIDTH_LIMIT_EXCEEDED(new HashMap<String, String>() {{put("BANDWIDTH_LIMIT_EXCEEDED","E509");}}),
    @JsonProperty("NETWORK_READ_TIMEOUT_ERROR,UNKNOWN")
    NETWORK_READ_TIMEOUT_ERROR(new HashMap<String, String>() {{put("NETWORK_READ_TIMEOUT_ERROR,UNKNOWN","E598");}}),
    @JsonProperty("NETWORK_CONNECTION_TIMEOUT_ERROR,UNKNOWN")
    NETWORK_CONNECTION_TIMEOUT_ERROR(new HashMap<String, String>() {{put("NETWORK_CONNECTION_TIMEOUT_ERROR,UNKNOWN","E599");}}),
    @JsonProperty("SQL_INJECTION")
    SQL_INJECTION(new HashMap<String, String>() {{put("SQL_INJECTION","991");}});

    /*@JsonProperty("FAILURE")
    FAILURE(new HashMap<String, String>() {{put("FAILURE","S001");}}),
    @JsonProperty("ACCESS_DENIED")
    ACCESS_DENIED,
     @JsonProperty("ARGUMENT_NOT_VALID")
    ARGUMENT_NOT_VALID(new HashMap<String, String>() {{put("USER_NOT_FOUND","E012");}}),
    @JsonProperty("NOT_EXISTS_AUTHORITY")
    NOT_EXISTS_AUTHORITY,
    @JsonProperty("BAD_REQUEST")
    BAD_REQUEST,*/

    private HashMap<String, String> value;
    private ApiStatus(HashMap<String, String> value) {this.value = value;}
    public HashMap<String, String> getValue() {return this.value;}
}
