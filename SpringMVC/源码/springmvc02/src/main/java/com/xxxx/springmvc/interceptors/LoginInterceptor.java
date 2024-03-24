package com.xxxx.springmvc.interceptors;

import com.xxxx.springmvc.vo.User;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         *  获取用户session 信息
         *    如果用户session 信息存在  请求合法放行处理
         *    如果用户session 信息不存在 请求非法 阻止目标方法执行
         */
        User user = (User) request.getSession().getAttribute("userInfo");
        if(null ==user){
            // 用户未登录 或者 session已失效
            response.sendRedirect(request.getContextPath()+"/login.jsp");
            return false;
        }
        return true;
    }
}
