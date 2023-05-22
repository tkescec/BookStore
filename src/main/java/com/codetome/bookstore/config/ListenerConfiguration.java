package com.codetome.bookstore.config;

import com.codetome.bookstore.listener.CustomSessionListener;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;

public class ListenerConfiguration {
    @Bean
    public ServletListenerRegistrationBean<CustomSessionListener> sessionListenerWithMetrics() {
        ServletListenerRegistrationBean<CustomSessionListener> listenerRegBean =
                new ServletListenerRegistrationBean<>();

        listenerRegBean.setListener(new CustomSessionListener());
        return listenerRegBean;
    }
}
