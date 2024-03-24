package com.xxxx.ssm;

import com.xxxx.ssm.exceptions.BusinessException;
import com.xxxx.ssm.exceptions.ParamsException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@Component
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView mv=new ModelAndView("error");
        mv.addObject("ex","默认错误信息...");

        if(ex instanceof ParamsException){
            ParamsException pe = (ParamsException) ex;
            mv.setViewName("params_error");
            mv.addObject("ex",pe.getMsg());
        }

        if(ex instanceof BusinessException){
            BusinessException be = (BusinessException) ex;
            mv.setViewName("busi_error");
            mv.addObject("ex",be.getMsg());
        }


        /**
         * 判断handler 响应结果  视图|json
         */
       /* response.getWriter().write("json");
        return null;*/

        return mv;
    }
}
