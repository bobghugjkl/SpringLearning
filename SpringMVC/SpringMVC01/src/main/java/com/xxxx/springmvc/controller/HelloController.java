package com.xxxx.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {
    /**
     * 请求映射地址  /hello.do
     * @return
     */
    @RequestMapping("hello")
    public ModelAndView hello(){
        ModelAndView mv=new ModelAndView();
        mv.addObject("hello", "hello spring mvc");
        mv.setViewName("hello");
        return mv;
    }
}
