package com.xxxx.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloController {

    @RequestMapping("hello")
    public ModelAndView hello(){
        ModelAndView mv=new ModelAndView();
        /**
         * 模型数据:Model
         * 视图: View
         */
        mv.addObject("hello","hello SpringMvc");
        mv.setViewName("hello");
        return mv;
    }


}
