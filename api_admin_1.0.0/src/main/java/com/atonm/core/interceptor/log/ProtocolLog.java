package com.atonm.core.interceptor.log;

import com.atonm.core.interceptor.BufferedResponseWrapper;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

public class ProtocolLog {
    private Date requestedTime;
    private Date responsedTime;
    private long diffTime;

    private String requestURL;
    private Map<String, String> requestHeaders = new LinkedHashMap<>();
    private Map<String, String> requestParameters = new LinkedHashMap<String, String>();

    private int statusCode;
    private String responseBody;
    private Map<String, String> responseHeaders = new LinkedHashMap<String, String>();

    public ProtocolLog(HttpServletRequest request) {
        requestedTime = new Date();
        requestURL = request.getRequestURL().toString();

        Enumeration enums = request.getHeaderNames();
        String key = null;
        String value = null;
        while (enums.hasMoreElements()) {
            key = (String)enums.nextElement();
            value = request.getHeader(key);
            requestHeaders.put(key, value);
        }

        enums = request.getParameterNames();
        while (enums.hasMoreElements()) {
            key = (String)enums.nextElement();
            value = request.getParameter(key);
            System.out.println(key + "=" + value);
            requestParameters.put(key, value);
        }
    }

    public void complete(BufferedResponseWrapper responseWrapper) {
        responsedTime = new Date();
        diffTime = responsedTime.getTime() - requestedTime.getTime();

        statusCode=responseWrapper.getStatusCode();
        responseBody=responseWrapper.getContent();
        responseHeaders.putAll(responseWrapper.getHeaderNameMap());
    }

    public Date getRequestedTime() {
        return requestedTime;
    }

    public Date getResponsedTime() {
        return responsedTime;
    }

    public long getDiffTime() {
        return diffTime;
    }

    public String getRequestURL() {
        return requestURL;
    }

    public Map<String, String> getRequestHeaders() {
        return requestHeaders;
    }

    public Map<String, String> getRequestParameters() {
        return requestParameters;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public Map<String, String> getResponseHeaders() {
        return responseHeaders;
    }

    public String getResponseBody() {
        return responseBody;
    }


}
