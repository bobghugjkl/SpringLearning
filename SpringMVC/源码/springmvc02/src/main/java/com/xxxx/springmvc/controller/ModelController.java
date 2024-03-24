package com.xxxx.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 请求域对象设置 API
 *    ModelAndView
 *    Model
 *    ModelMap
 *    Map
 *    Request
 */
@Controller
public class ModelController {


    @RequestMapping("m01")
    public ModelAndView m01(){
        ModelAndView mv=new ModelAndView();
        /**
         * 模型数据:Model
         * 视图: View
         */
        mv.addObject("hello","hello SpringMvc");
        mv.setViewName("hello");
        return mv;
    }

    @RequestMapping("m02")
    public String m02(HttpServletRequest request){
       request.setAttribute("hello","hello m02");
       // 返回视图名
       return "hello";
    }

    @RequestMapping("m03")
    public String m03(ModelMap modelMap){
        modelMap.addAttribute("hello","hello m03");
        // 返回视图名
        return "hello";
    }

    @RequestMapping("m04")
    public String m04(Model model){
        model.addAttribute("hello","hello m04");
        // 返回视图名
        return "hello";
    }

    @RequestMapping("m05")
    public String m05(Map map){
        map.put("hello","hello m05");
        // 返回视图名
        return "hello";
    }



}
