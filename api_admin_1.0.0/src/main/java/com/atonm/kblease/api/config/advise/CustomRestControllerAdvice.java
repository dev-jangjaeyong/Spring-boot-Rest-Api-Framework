package com.atonm.kblease.api.config.advise;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import com.atonm.kblease.api.exception.APIException;
import com.atonm.kblease.api.exception.DaemonException;
import org.hibernate.service.spi.ServiceException;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.atonm.core.api.ApiResponse;
import com.atonm.core.api.ApiStatus;
import com.atonm.core.dto.BindErrorsDTO;

/**
 * @author jang jea young
 * @since 2018-08-10
 */
@ControllerAdvice
@RestController
public class CustomRestControllerAdvice extends ResponseEntityExceptionHandler {
    private final Properties properties;

    @Autowired
    public CustomRestControllerAdvice(@Qualifier("MessageProperties") Properties properties) {
        this.properties = properties;
    }

    /**
     * ModelAttribute 혹은 RequestBody에 설정된 DTO의 Validation관련 어노테이션의 조건을 충족시키지 못하면 발생한다.
     * @see javax.validation.constraints
     */
    @Override
    @ResponseBody
    protected ResponseEntity<Object> handleBindException(
            BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List resultList = ex.getFieldErrors().stream().map(error ->
                new BindErrorsDTO(error.getField(), error.getDefaultMessage(), error.getRejectedValue())
        ).collect(Collectors.toList());

        ApiResponse apiResponse = ApiResponse.argumentOfErroneousDataTypes(resultList);

        return ResponseEntity.badRequest().body(apiResponse);
    }

    /**
     *  RequestParam의 required 값이 true인데, 해당 Param이 전달되지 않으면 발생한다.
     */
    @Override
    @ResponseBody
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List resultList = Collections.singletonList(new BindErrorsDTO(ex.getParameterName(), properties.getProperty("message.not-null"), null));

        ApiResponse apiResponse = ApiResponse.missingRequiredParameter(resultList);
        apiResponse.setApiCode("E021");

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<String>();
        String field = null;
        String rejectValue = null;
        String message = null;

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
            field = error.getField();
            rejectValue = error.getRejectedValue().toString();
            message = error.getDefaultMessage();
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        //BindErrorsDTO ApiError = new BindErrorsDTO(field, message, rejectValue);
        List resultList = Collections.singletonList(new BindErrorsDTO(field, message, rejectValue));
        ApiResponse apiResponse = ApiResponse.argumentOfErroneousDataTypes(resultList);
        apiResponse.setApiCode("E023");

        return ResponseEntity.badRequest().body(apiResponse);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        Throwable mostSpecificCause = ex.getMostSpecificCause();
        ErrorMessage errorMessage;
        if (mostSpecificCause != null) {
            String exceptionName = mostSpecificCause.getClass().getName();
            String message = mostSpecificCause.getMessage();
            errorMessage = new ErrorMessage(exceptionName, ex);
        } else {
            errorMessage = new ErrorMessage(ex.getMessage());
        }
        return new ResponseEntity(errorMessage, headers, status);
    }
    
    /**
     * 예기치 못한 오류에 대한 처리
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler
    public ApiResponse defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception  {
    	e.printStackTrace();
    	if(e instanceof APIException) {
    		return ApiResponse.taskException(e.getMessage());
    	}else if(e instanceof ServiceException) {
    		return ApiResponse.taskException(ApiStatus.SERVICE_UNAVAILABLE,"서비스 이용이 불가능합니다.");
    	}else if(e instanceof DaemonException) {
    		return ApiResponse.taskException(((DaemonException) e).getApiStatus(), e.getMessage());
    	}
    	
    	return ApiResponse.error();
    }
}
