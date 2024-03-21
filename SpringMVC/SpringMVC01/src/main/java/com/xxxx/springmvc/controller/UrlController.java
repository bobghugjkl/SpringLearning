package com.xxxx.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("url")
public class UrlController {
    @RequestMapping(value = {"u03_01","u03_02"},method = {RequestMethod.POST})
    public ModelAndView u01(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("hello","u01");
        modelAndView.setViewName("hello");
        return modelAndView;
    }
}
