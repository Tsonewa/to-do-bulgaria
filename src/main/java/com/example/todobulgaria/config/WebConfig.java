package com.example.todobulgaria.config;

import com.example.todobulgaria.interceptors.LastModelAndViewInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LastModelAndViewInterceptor lastModelAndViewInterceptor;


    public WebConfig(LastModelAndViewInterceptor lastModelAndViewInterceptor1) {
        this.lastModelAndViewInterceptor = lastModelAndViewInterceptor1;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(lastModelAndViewInterceptor);
    }
}
