package com.xxxx.ssm.controller;

import com.xxxx.ssm.exceptions.BusinessException;
import com.xxxx.ssm.exceptions.ParamsException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    @ExceptionHandler
    public String exc(HttpServletRequest request, Exception ex){
        request.setAttribute("ex", ex);
        if(ex instanceof ParamsException){
            return "params_error";
        }
        if(ex instanceof BusinessException){
            return "busi_error";
        }
        return "error";
    }
}
