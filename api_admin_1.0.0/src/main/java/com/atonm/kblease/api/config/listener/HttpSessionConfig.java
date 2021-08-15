package com.atonm.kblease.api.config.listener;

import com.atonm.kblease.api.config.property.AdditionalProperty;
import com.atonm.core.context.AppContextManager;
import com.atonm.core.interceptor.ClientInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Date;

@Configuration
class HttpSessionConfig {
    private static final Logger logger = LoggerFactory.getLogger(HttpSessionConfig.class);
    @Bean                       // bean for http session listener
    public HttpSessionListener httpSessionListener() {
        return new HttpSessionListener() {
            @Override
            public void sessionCreated(HttpSessionEvent se) {               // This method will be called when session created
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
                ClientInfo clientInfo = new ClientInfo(request);
                logger.info("Session request Full Path +" + clientInfo.getFullURL());
                //if(request.getHeader("referer").contains("/") || clientInfo.getPathInfo().contains("/affuser/info")) {
                    final int EXPIRATION_TIME = AppContextManager.getBean(AdditionalProperty.class).getSessionTimeout();
                    se.getSession().setMaxInactiveInterval(999999999);
                    logger.info("Session Created with session id+" + se.getSession().getId());
                    logger.info("Session ID : ".concat(se.getSession().getId()).concat(" created at ").concat(new Date().toString()));
                //}
            }
            @Override
            public void sessionDestroyed(HttpSessionEvent se) {         // This method will be automatically called when session destroyed
                logger.info("Session Destroyed, Session id:" + se.getSession().getId());
                logger.info("Session ID : ".concat(se.getSession().getId()).concat(" destroyed at ").concat(new Date().toString()));
                //SecurityContextHolder.clearContext();
            }
        };
    }
    /*@Bean                   // bean for http session attribute listener
    public HttpSessionAttributeListener httpSessionAttributeListener() {
        return new HttpSessionAttributeListener() {
            @Override
            public void attributeAdded(HttpSessionBindingEvent se) {            // This method will be automatically called when session attribute added
                System.out.println("Attribute Added following information");
                System.out.println("Attribute Name:" + se.getName());
                System.out.println("Attribute Value:" + se.getName());
            }
            @Override
            public void attributeRemoved(HttpSessionBindingEvent se) {      // This method will be automatically called when session attribute removed
                System.out.println("attributeRemoved");
            }
            @Override
            public void attributeReplaced(HttpSessionBindingEvent se) {     // This method will be automatically called when session attribute replace
                System.out.println("Attribute Replaced following information");
                System.out.println("Attribute Name:" + se.getName());
                System.out.println("Attribute Old Value:" + se.getValue());
            }
        };
    }*/
}
