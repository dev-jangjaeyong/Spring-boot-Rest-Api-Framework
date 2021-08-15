package com.atonm.core.interceptor;

import com.atonm.core.api.ApiStatus;
import com.atonm.core.common.constant.Constant;
import com.atonm.core.worker.LogWorker;
import com.atonm.core.worker.MenuLogWorker;
import com.atonm.core.worker.MenuQueueManager;
import com.atonm.core.worker.QueueManager;
import com.atonm.kblease.api.common.exception.UserInfoNotFoundException;

import com.atonm.core.api.ApiResponse;
import com.atonm.core.interceptor.Sql.SQLFilter;
import com.atonm.core.interceptor.xss.XssFilter;
import com.atonm.kblease.api.log.dto.AccessLogDTO;
import com.atonm.kblease.api.log.dto.ActionLogDTO;
import com.atonm.kblease.api.utils.ModelMapperUtils;
import com.atonm.kblease.api.utils.SecurityUtils;
import com.atonm.kblease.api.utils.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author jang jea young
 * @since 2018-08-09.
 */
public class ApiFilter implements Filter {
    private static long requestId = 0; // API 요청 ID : 1씩 증가 (요청 및 응답 로그 구별을 위해서 사용)
    private static int queueSize = 1000;
    static ExecutorService actionExecutors = Executors.newCachedThreadPool();
    static ExecutorService accessExecutors = Executors.newCachedThreadPool();
    private static final Logger log_apiFilter = LoggerFactory.getLogger(ApiFilter.class);
    //static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);

    @Override
    public void init(javax.servlet.FilterConfig filterConfig) throws ServletException {
        QueueManager.setConfig(queueSize);
        actionExecutors.execute(new LogWorker());
        accessExecutors.execute(new MenuLogWorker());
        //scheduler.scheduleAtFixedRate(new HealthCheckWorker(), 1, 10, TimeUnit.SECONDS);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            LocalDateTime requestDate = LocalDateTime.now();
            HttpServletRequest httprequest = (HttpServletRequest) request;
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;

            ClientInfo clientInfo = new ClientInfo(httprequest);
            HttpSession session = httprequest.getSession(false);
            int sessionType = 0;
            if(session == null) {
                log_apiFilter.debug("========================================================");
                log_apiFilter.debug("세션 없음");
                log_apiFilter.debug("========================================================\n");
                sessionType = 99;
            }else if(session.isNew()) {
                /*if(httprequest.getHeader("referer").contains("/") || clientInfo.getPathInfo().contains("/affuser/info")) {
                    log_apiFilter.debug("========================================================");
                    log_apiFilter.debug("새로 생성된 세션");
                    log_apiFilter.debug("========================================================\n");
                    sessionType = 1;
                }else{
                    log_apiFilter.debug("========================================================");
                    log_apiFilter.debug("따로 생성된 시큐리티 세션");
                    log_apiFilter.debug("========================================================\n");
                    sessionType = 1;
                    // chain.doFilter(request, response);
                    //ApiResponse _apiResponse = ApiResponse.expireSession();
                    //throw new UserInfoNotFoundException(_apiResponse.getApiCode());
                }*/
                log_apiFilter.debug("========================================================");
                log_apiFilter.debug("새로 생성된 세션이거나 따로 생성된 시큐리티 세션");
                log_apiFilter.debug("========================================================\n");
                sessionType = 1;
            }else{
                log_apiFilter.debug("========================================================");
                log_apiFilter.debug("기존 생성 사용");
                log_apiFilter.debug("========================================================\n");

                /*String _authToken = httpServletResponse.getHeader("authorization");

                if(!StringUtil.isEmpty(_authToken)) {
                    ApiResponse _apiResponse = ApiResponse.expireSession();
                    throw new UserInfoNotFoundException(_apiResponse.getApiCode());
                }*/

                if(clientInfo.getMenuUrl().contains("logout")){
                    session.invalidate();
                }else{
                    sessionType = 2;
                }
            }

            log_apiFilter.debug("========================================================");
            log_apiFilter.debug("httprequest.get _method : " + httprequest.getHeader("_method"));
            log_apiFilter.debug("httprequest.get Method : " + httprequest.getMethod());
            log_apiFilter.debug("========================================================\n");

            clientInfo.printClientInfo();

            HashMap<String, String[]> paramMap = new HashMap<String, String[]>();
            //paramMap.put("pid", new String[]{String.valueOf(request.getAttribute("pid"))});
            paramMap.put("pid", new String[]{String.valueOf(request.getParameter("pid"))});
            paramMap.put("txid", new String[]{generateRequestId()});

            String currentUserNo = "";
            try{
                if(SecurityUtils.getCurrentUserInfo() != null) {
                    currentUserNo = SecurityUtils.getCurrentUserInfo().getUserId() != null ? String.valueOf(SecurityUtils.currentUserNo()) : "";
                }
            }catch (Exception e) {
                currentUserNo = "";
            }

            paramMap.put("currentUserNo", new String[]{currentUserNo});
            request = new WrappedRequestWithParameter(httprequest, paramMap);

            RequestWrapper wrapper = new RequestWrapper((HttpServletRequest)request);
            wrapper.setAttribute("requestBody",wrapper.getBody());
            String requestBody = "";

            if(checkSqlInjection(wrapper)) {
                ApiStatus sqlInjectionApiStatus = ApiStatus.SQL_INJECTION;
                throw new UserInfoNotFoundException(sqlInjectionApiStatus.getValue().get(sqlInjectionApiStatus.toString()));
            }

            if(wrapper.getMethod().equalsIgnoreCase("GET")) {
                //wrapper.resetInputStream(XssFilter.cleanXSS(wrapper.getQueryString()).getBytes());
                requestBody = wrapper.getQueryString();
                XssFilter.cleanXSS(wrapper.getQueryString());
            } else {
                requestBody = wrapper.getBody();
                wrapper.resetInputStream(XssFilter.cleanBoardXSS(wrapper.getBody()).getBytes());
            }

            String body = IOUtils.toString(wrapper.getReader());
            BufferedResponseWrapper bufferedResponse = new BufferedResponseWrapper(httpServletResponse);
            String logType = "menu";

            if(!clientInfo.getFullURL().contains("menu-url")) {
                if(clientInfo.getFullURL().contains("swagger") || clientInfo.getFullURL().contains("api-docs") || clientInfo.getFullURL().contains("download") || clientInfo.getFullURL().contains("carimage") || clientInfo.getFullURL().contains("actuator") || clientInfo.getFullURL().contains("user-role")) {
                    logType = "swagger";
                    chain.doFilter(request, response);
                }else if(clientInfo.getFullURL().contains("bridge")){
                    logType = "bridge";
                    chain.doFilter(request, response);
                }else if(clientInfo.getFullURL().contains("actuator")){
                    logType = "actuator";
                    chain.doFilter(request, response);
                }else if(clientInfo.getFullURL().contains("user-role")){
                    logType = "roleFilter";
                    chain.doFilter(request, response);
                }else{
                    logType = "api";
                    if(!"".equals(body)) {
                        try {
                            JSONObject oldJsonObject = new JSONObject(body);
                            oldJsonObject.put("currentUserNo", currentUserNo);
                            if(!oldJsonObject.has("pid"))
                                oldJsonObject.put("pid", Constant.PID);
                            wrapper.resetInputStream(oldJsonObject.toString().getBytes());

                            request.setAttribute("pid", oldJsonObject.get("pid"));
                            request.setAttribute("channel", oldJsonObject.get("channel"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    chain.doFilter(wrapper, bufferedResponse);
                }
            }

            if(!logType.equalsIgnoreCase("swagger") && !logType.equalsIgnoreCase("bridge") && !logType.equalsIgnoreCase("actuator")) {
                final StringBuilder logMessage = new StringBuilder();
                logMessage.append(" [RESPONSE:").append(bufferedResponse.getContent()).append("]");
                for(String key: bufferedResponse.getHeaderNameMap().keySet()) {
                    logMessage.append(" [HEADERS:").append("key : " + key + ", value : " + bufferedResponse.getHeaderNameMap().get(key)).append("]");
                }

                log_apiFilter.debug("========================================================");
                log_apiFilter.debug(logMessage.toString());
                log_apiFilter.debug("========================================================\n");

                String _chnnlCode;
                if(request.getAttribute("channel") != null) {
                    _chnnlCode = String.valueOf(request.getAttribute("channel"));
                }else if(request.getParameter("channel") != null) {
                    _chnnlCode = String.valueOf(request.getParameter("channel"));
                }else{
                    _chnnlCode = "";
                }

                ApiResponse apiRespons = null;
                if(bufferedResponse.getContent() != null) {
                    ObjectMapper mapper = new ObjectMapper();
                    if(bufferedResponse.getContent().isEmpty() && clientInfo.getFullURL().contains("menu-url")) {
                        bufferedResponse.setStatus(200);
                        apiRespons = new ApiResponse();
                        apiRespons.setApiCode("200");
                    }else{
                        if(StringUtil.isEmpty(bufferedResponse.getContent())) {
                            apiRespons = new ApiResponse();
                        }else{
                            apiRespons = mapper.readValue(bufferedResponse.getContent(), ApiResponse.class);
                        }
                    }
                }

                LocalDateTime responseDate = LocalDateTime.now();
                Object _ret = this.getLogObject(apiRespons, currentUserNo, _chnnlCode, clientInfo, bufferedResponse, request, StringUtil.isEmpty(requestBody) ? new ObjectMapper().writeValueAsString(wrapper.getParameterMap()) : requestBody, requestDate, responseDate);
                if(logType.equalsIgnoreCase("menu")) {
                    MenuQueueManager.getInstance().offerLog(ModelMapperUtils.map(_ret, AccessLogDTO.class));
                }else{
                    if(!clientInfo.getPathInfo().contains("/trans")) {
                        ActionLogDTO actionLogDTO = ModelMapperUtils.map(_ret, ActionLogDTO.class);
                        actionLogDTO.setReqDt(requestDate);
                        actionLogDTO.setResDt(responseDate);
                        QueueManager.getInstance().offerLog(actionLogDTO);
                    }
                }
            }
        }
    }

    private Object getLogObject(ApiResponse apiRespons, String currentUserNo, String _chnnlCode, ClientInfo clientInfo, BufferedResponseWrapper bufferedResponse, ServletRequest request, String requestBody, LocalDateTime requestDate, LocalDateTime responseDate) throws UnsupportedEncodingException {
        HashMap<String, Object> _ret = new HashMap<String, Object>();

        String logType = "api";
        if(clientInfo.getFullURL().contains("menu-url")) {
            logType = "menu";
        }

        String  attPid  =   String.valueOf(request.getAttribute("pid"));
        if (StringUtil.isEmpty(attPid) || "null".equals(attPid)) {
            attPid  =   String.valueOf(request.getParameter("pid"));
        }

        _ret.put("pid"          ,   attPid);
        _ret.put("channel"    ,   _chnnlCode);
        _ret.put("actionUrl"    ,   clientInfo.getPathInfo());
        _ret.put("accessUrl"    ,   clientInfo.getMenuUrl());
        _ret.put("actionMethod" ,   clientInfo.getRequestMethod());
        _ret.put("ip"           ,   clientInfo.getClientIpAddr());
        _ret.put("userId"       ,   currentUserNo.isEmpty() ? null : currentUserNo);
        _ret.put("brwsrInfo"    ,   clientInfo.getClientBrowser());
        _ret.put("osClass"      ,   clientInfo.getClientOS());
        _ret.put("resultCode"   ,   String.valueOf(apiRespons.getApiCode()));
        _ret.put("resultStatus"      ,   String.valueOf(apiRespons.getApiStatus()));
        _ret.put("reqDt", requestDate);
        _ret.put("resDt", responseDate);

        if(logType.equalsIgnoreCase("api")) {
            _ret.put("reqArgs", requestBody);
            _ret.put("resMsg", bufferedResponse.getContent());
        }else{
            _ret.put("reqArgs", requestBody);
        }

        _ret.put("logType", logType);

        return _ret;
    }

    @Override
    public void destroy() {
        actionExecutors.shutdown();
        accessExecutors.shutdown();
    }

    @SuppressWarnings("static-access")
    private String generateRequestId() {
        String token = UUID.randomUUID().toString().toUpperCase().replace("-", "");
        return String.valueOf(++this.requestId) + token;
    }


    private static final class BufferedServletInputStream extends ServletInputStream {

        private ByteArrayInputStream bais;

        public BufferedServletInputStream(ByteArrayInputStream bais) {
            this.bais = bais;
        }

        @Override
        public int available() {
            return this.bais.available();
        }

        @Override
        public int read() {
            return this.bais.read();
        }

        @Override
        public int read(byte[] buf, int off, int len) {
            return this.bais.read(buf, off, len);
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener listener) {

        }
    }

    private boolean checkSqlInjection(RequestWrapper wrapper) {
        boolean _ret = false;
        if(wrapper.getMethod().equalsIgnoreCase("GET")) {
            _ret = SQLFilter.sqlHandler(wrapper.getQueryString());
        }else{
            _ret = SQLFilter.sqlJsonStringChecker(wrapper.getBody());
        }
        return _ret;
    }
}
