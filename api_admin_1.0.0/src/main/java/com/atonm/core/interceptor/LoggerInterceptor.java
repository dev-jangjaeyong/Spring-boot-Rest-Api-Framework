package com.atonm.core.interceptor;

import com.atonm.kblease.api.utils.DateUtil;
import com.atonm.core.interceptor.log.ProtocolLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;


public class LoggerInterceptor extends HandlerInterceptorAdapter {
    private static final Logger log_usual = LoggerFactory.getLogger(LoggerInterceptor.class);
    private Date requestedTime;
    private Date responsedTime;
    private long diffTime;
    private Map<String, String> responseHeaders = new LinkedHashMap<>();
    private String responseBody;
    private String txid;
    ProtocolLog protocolLog;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //protocolLog = new ProtocolLog(request);
        requestedTime = new Date();
        response.addHeader("_s", DateUtil.getDate(requestedTime, "yyyy-MM-dd HH:mm:ss:SSS"));
        //RequestWrapper requestWrapper = new RequestWrapper(request);
        //protocolLog = new ProtocolLog(request);

        /*String body = requestWrapper.getBody();
        String clientIP = requestWrapper.getRemoteHost();
        int clientPort = requestWrapper.getRemotePort();
        String uri = requestWrapper.getRequestURI();*/

        /*String clientIP = requestWrapper.getRemoteHost();
        int clientPort = requestWrapper.getRemotePort();
        String uri = requestWrapper.getRequestURI();
        txid = requestWrapper.getRequest().getParameter("txid");

        if(request.getMethod().equalsIgnoreCase("POST")
                || request.getMethod().equalsIgnoreCase("PUT")
                || request.getMethod().equalsIgnoreCase("DELETE")) {
            System.out.println("========================================================");
            System.out.println(request.getAttribute("requestBody"));
            System.out.println(clientIP);
            System.out.println(clientPort);
            System.out.println(uri);
            System.out.println(txid);
            System.out.println("========================================================\n");
        }else{
            System.out.println("========================================================");
            ObjectMapper mapper = new ObjectMapper();

            HashMap<String, String> parameter = new HashMap<String, String>();
            Map<String, String[]> requestParamMap = request.getParameterMap();


            for(String key : requestParamMap.keySet()) {
                parameter.put(key, requestParamMap.get(key)[0]);
            }

            String jsonInUserInfo = mapper.writeValueAsString(parameter);
            System.out.println(jsonInUserInfo);
            System.out.println(clientIP);
            System.out.println(clientPort);
            System.out.println(request.getHeader("User-Agent"));
            System.out.println(uri);
            System.out.println(txid);
            System.out.println("========================================================\n");
        }*/



        log_usual.info("Before handling the request");
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log_usual.info("After handling the request");
        //protocolLog.complete((BufferedResponseWrapper) response);

        /*response.addHeader("responsePeriod", String.valueOf(protocolLog.getDiffTime()));
        response.addHeader("requestTime", DateUtil.getDate(protocolLog.getRequestedTime(), "yyyy-MM-dd HH:mm:ss:SSS"));
        response.addHeader("responseTime", DateUtil.getDate(protocolLog.getResponsedTime(), "yyyy-MM-dd HH:mm:ss:SSS"));
        response.setHeader("responsePeriod", String.valueOf(protocolLog.getDiffTime()));*/
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        /*responsedTime = new Date();
        diffTime = responsedTime.getTime() - requestedTime.getTime();
        ObjectMapper mapper = new ObjectMapper();

        System.out.println("========================================================\n");
        System.out.println(diffTime);
        System.out.println("========================================================\n");

        ApiResponse apiResponse;
        apiResponse = mapper.readValue(((BufferedResponseWrapper) response).bos.toString(), ApiResponse.class);*/
        /*protocolLog.complete((BufferedResponseWrapper) response);

        response.addHeader("responsePeriod", String.valueOf(protocolLog.getDiffTime()));
        response.addHeader("requestTime", DateUtil.getDate(protocolLog.getRequestedTime(), "yyyy-MM-dd HH:mm:ss:SSS"));
        response.addHeader("responseTime", DateUtil.getDate(protocolLog.getResponsedTime(), "yyyy-MM-dd HH:mm:ss:SSS"));
        response.setHeader("responsePeriod", String.valueOf(protocolLog.getDiffTime()));*/

        /*apiResponse.setDifftime(String.valueOf(protocolLog.getDiffTime()));
        apiResponse.setRequestTime(DateUtil.getDate(protocolLog.getRequestedTime(), "yyyy-MM-dd HH:mm:ss:SSS"));
        apiResponse.setResponseTime(DateUtil.getDate(protocolLog.getResponsedTime(), "yyyy-MM-dd HH:mm:ss:SSS"));*/

        /*HttpServletResponse _response = response;
        ((BufferedResponseWrapper) _response).bos.reset();
        ((BufferedResponseWrapper) _response).original.getOutputStream().flush();
        ((BufferedResponseWrapper) _response).tee.flush();
        response.getOutputStream().write(mapper.writeValueAsString(apiResponse).getBytes("UTF-8"));
        response.getOutputStream();

        response.getOutputStream().close();*/

        super.afterCompletion(request, response, handler, ex);
        log_usual.info("After rendering the view");
    }
}
