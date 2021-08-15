package com.atonm.core.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author jang jea young
 * @since 2021-05-08
 */
@Component
public class AppContextManager implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext _applicationContext) {
        applicationContext = _applicationContext;
    }

    public static ApplicationContext getAppContext() {
        return applicationContext;
    }

    private static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }

    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    public static <T> T getBean(String beanName, Class<T> requiredType) {
        return applicationContext.getBean(beanName, requiredType);
    }

    public static Object getServiceBean(String targetClassName) {
        String serviceBeanName = Character.toLowerCase(targetClassName.charAt(0)) + targetClassName.substring(1) + "Service";
        return getBean(serviceBeanName);
    }
}
