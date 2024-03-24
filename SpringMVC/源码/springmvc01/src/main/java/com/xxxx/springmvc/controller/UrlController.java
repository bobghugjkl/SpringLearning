package com.xxxx.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


/**
 *   @RequestMapping:
 *      声明级别:
 *         类级别
 *         方法级别    方法级别uri
 *         类级别+方法级别   类级别uri +方法级别uri
 *     没有声明请求类型  资源可以处理Get + Post
 *       可以显示声明方法处理的请求类型   声明method 属性值  支持多种请求类型
 */
@Controller
@RequestMapping("url")
public class UrlController {

    //  ip:port/springmvc01/url/u01.do
    @RequestMapping("u01")
    public ModelAndView u01(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("hello","u01");
        modelAndView.setViewName("hello");
        return modelAndView;
    }

    //  ip:port/springmvc01/url/u02.do
    @RequestMapping("/u02")
    public ModelAndView u02(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("hello","u02");
        modelAndView.setViewName("hello");
        return modelAndView;
    }


    // 支持一个方法多个uri 方法绑定操作
    @RequestMapping(value = {"u03_01","u03_02"})
    public ModelAndView u03(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("hello","u03");
        modelAndView.setViewName("hello");
        return modelAndView;
    }


    // 只能处理post 请求
    @RequestMapping(value = "u04",method = {RequestMethod.POST})
    public ModelAndView u04(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("hello","u04");
        modelAndView.setViewName("hello");
        return modelAndView;
    }


    // ip:port/springmvc01/url.do?u05
    @RequestMapping(params = "u05")
    public ModelAndView u05(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("hello","u05");
        modelAndView.setViewName("hello");
        return modelAndView;
    }

}
