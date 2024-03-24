package com.xxxx.springmvc.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor1 implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("目标Handler执行前执行MyInterceptor1-->preHandle 方法...");
        /**
         *  true:执行handler 方法
         *  false: 阻止目标Handler  方法执行
         */
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("目标Handler执行后,视图生成前执行MyInterceptor1-->postHandle 方法...");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("目标Handler执行后,视图生成后执行MyInterceptor1-->afterCompletion 方法...");
    }
}
