package com.xxxx.springmvc.controller;


import com.xxxx.springmvc.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.annotation.Documented;

/**
 * 参数绑定（重点掌握前4种）
 *    基本类型（包装类型）
 *    String | Date
 *    数组
 *    JavaBean
 *    List|Set|Map(了解)
 */
@Controller
public class ParamsController {

    /**
     * 基本类型
     *    参数值必须存在  如果没有指定参数值 也没有配置默认值  此时方法 500错误!!!
     * @param age
     * @param s
     */
    @RequestMapping("p01")
    public void p01(int age,double s){
        System.out.println("age:"+age+":s:"+s);
    }


    /**
     * 基本类型
     *    参数值必须存在  如果没有指定参数值 也没有配置默认值  此时方法 500错误!!!
     *     防止500参数错误 可以使用@RequestParam 配置参数默认值
     * @param age
     * @param s
     */
    @RequestMapping("p02")
    public void p02(@RequestParam(defaultValue = "10") int age,@RequestParam(defaultValue = "60.5") double s){
        System.out.println("age:"+age+":s:"+s);
    }

    /**
     * 基本类型
     *    参数值必须存在  如果没有指定参数值 也没有配置默认值  此时方法 500错误!!!
     *     防止500参数错误 可以使用@RequestParam 配置参数默认值  同时可以通过name 属性 给形参取别名
     * @param age
     * @param s
     */
    @RequestMapping("p03")
    public void p03(@RequestParam(name = "a",defaultValue = "10") int age,@RequestParam(defaultValue = "60.5") double s){
        System.out.println("age:"+age+":s:"+s);
    }

    /**
     * 字符串参数绑定
     *    客户端请求参数名与方法形参名一致  默认参数值为null
     *    @RequestParam  可以设置形参的别名  参数默认值
     * @param userName
     * @param userPwd
     */
    @RequestMapping("p04")
    public void p04(String userName,String userPwd){
        System.out.println("userName:"+userName+":userPwd:"+userPwd);
    }

    /**
     * 包装参数绑定(推荐使用包装类型!!!)
     *    客户端请求参数名与方法形参名一致  默认参数值为null
     *    @RequestParam  可以设置形参的别名  参数默认值
     * @param age
     * @param s
     */
    @RequestMapping("p05")
    public void p04(Integer age, Double s){
        System.out.println("age:"+age+":s:"+s);
    }


    // 传参形式: ids=10&ids=20&ids=30
    @RequestMapping("p06")
    public void p06(Integer[] ids){
        for (Integer id : ids) {
            System.out.println(id);
        }
    }


    /**
     * 客户端参数名称与user 属性名一致即可
     * @param user
     */
    @RequestMapping("p07")
    public void p07(User user){
        System.out.println(user);
    }

    /**
     * List集合参数绑定  使用JavaBean进行包装
     * @param user
     */
    /*@RequestMapping("p08")
    public void p08(User user){
        user.getIds().forEach(id->{
            System.out.println(id);
        });
    }


    @RequestMapping("p09")
    public void p09(User user){
        user.getMap().forEach((k,v)->{
            System.out.println("k:"+k+":v:"+v);
        });
    }*/


}
