package com.test;

import com.xxxx.service.UserService;
import com.xxxx.service.UserService02;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Start01 {
    public static void main(String[] args) {
        //得到上下文环境
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
        //通过id属性值得到指定的bean对象,默认是object类型所以我们强转一下
        UserService userService = (UserService) ac.getBean("userService");
//        调用实例好的javabean对象
        userService.test();
        UserService02 userService02 = (UserService02)ac.getBean("userService02");
        userService02.test();
    }
}
