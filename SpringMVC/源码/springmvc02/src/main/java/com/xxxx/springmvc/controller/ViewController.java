package com.xxxx.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 转发与重定向
 */
@Controller
public class ViewController {

    // 页面重定向
    @RequestMapping("queryView1")
    public String queryView1(){
        return "redirect:v1.jsp";
    }


    // 重定向参数携带
    @RequestMapping("queryView2")
    public String queryView2(){
        return "redirect:v1.jsp?a=admin&b=123456";
    }


    // 重定向如果参数存在中文 此时可能会出现乱码
    @RequestMapping("queryView3")
    public String queryView3(){
        return "redirect:v1.jsp?a=奥利给&b=123456";
    }

    // 重定向如果参数存在中文 此时可能会出现乱码  通过声明 RedirectAttributes 类型参数 携带重定向参数
    @RequestMapping("queryView4")
    public String queryView4(RedirectAttributes redirectAttributes){
        redirectAttributes.addAttribute("a","奥利给");
        redirectAttributes.addAttribute("b","123456");
        return "redirect:v1.jsp";
    }

    // 重定向如果参数存在中文 此时可能会出现乱码
    @RequestMapping("queryView5")
    public ModelAndView queryView5(){
        ModelAndView mv=new ModelAndView();
        mv.setViewName("redirect:v1.jsp");
        mv.addObject("a","奥利给");
        mv.addObject("b","123456");
        return mv;
    }


    @RequestMapping("queryView6")
    public ModelAndView queryView6(){
        ModelAndView mv=new ModelAndView();
        mv.setViewName("forward:test.do");
        return mv;
    }

    @RequestMapping("queryView7")
    public ModelAndView queryView7(){
        ModelAndView mv=new ModelAndView();
        mv.setViewName("forward:test.do?a=admin&b=123456");
        return mv;
    }

    @RequestMapping("queryView8")
    public ModelAndView queryView8(){
        ModelAndView mv=new ModelAndView();
        mv.setViewName("redirect:test.do");
        mv.addObject("a", "admin");
        mv.addObject("b", "奥利给");
        return mv;
    }




}
