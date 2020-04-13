package com.blesk.accountservice.Component.ApplicationContext;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextProviderImpl implements ApplicationContextProvider {

    private static ApplicationContext APLICATION_CONTEXT;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        ApplicationContextProviderImpl.APLICATION_CONTEXT = context;
    }

    public static Object getBean(Class c) {
        return ApplicationContextProviderImpl.APLICATION_CONTEXT.getBean(c);
    }

    public static Object getBean(String qualifier, Class c) {
        return ApplicationContextProviderImpl.APLICATION_CONTEXT.getBean(qualifier , c);
    }
}