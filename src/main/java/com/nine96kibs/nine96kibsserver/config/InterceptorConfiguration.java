package com.nine96kibs.nine96kibsserver.config;

import com.nine96kibs.nine96kibsserver.interceptor.SessionInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class InterceptorConfiguration implements WebMvcConfigurer {
    public final static String SESSION_KEY = "user";

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SessionInterceptor()).excludePathPatterns("/login", "/index", "/signUp", "/register", "/error", "/**/*.css", "/**/*.js", "/**/*.png", "/**/*.gif", "/**/*.jpg", "/**/*.jpeg", "/font/**").addPathPatterns("/**");
    }
}
