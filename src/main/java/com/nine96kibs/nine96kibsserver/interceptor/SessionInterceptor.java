package com.nine96kibs.nine96kibsserver.interceptor;

import com.nine96kibs.nine96kibsserver.config.InterceptorConfiguration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session!=null && session.getAttribute(InterceptorConfiguration.SESSION_KEY) != null){
            return true;
        }
        response.setHeader("NotLogin", "True");
        return false;
    }
}
