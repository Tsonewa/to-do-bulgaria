package com.example.todobulgaria.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LastModelAndViewInterceptor implements HandlerInterceptor {

    public static final String LAST_MODEL_VIEW_ATTRIBUTE = LastModelAndViewInterceptor.class.getName() + ".lastModelAndView";

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        request.getSession(true).setAttribute(LAST_MODEL_VIEW_ATTRIBUTE, modelAndView);
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

}
