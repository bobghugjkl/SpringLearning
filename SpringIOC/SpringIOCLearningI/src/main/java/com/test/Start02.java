package com.test;

import com.xxxx.App;
import com.xxxx.Dao.UserDao;
import com.xxxx.service.UserService;
import com.xxxx.service.UserService02;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class Start02 {
    /*
    配置文件加载
    1.相对路径
    2.绝对路径
    3.多配置加载
    4.总的配置文件加载
     */
    public static void main(String[] args) {
        //相对路径
        //ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
//        绝对路径
        //ApplicationContext ac = new FileSystemXmlApplicationContext("E:\\javaCode\\Spring\\SpringIOC\\SpringIOCLearningI\\src\\main\\resources\\spring.xml");
        //d多配置加载
//        ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml","beans.xml");
//        总的配置文件
        ApplicationContext ac = new ClassPathXmlApplicationContext("service.xml");
        UserService userService = (UserService) ac.getBean("userService");
//        调用实例好的javabean对象
        userService.test();
        UserService02 userService02 = (UserService02)ac.getBean("userService02");
        userService02.test();
        UserDao userDao = (UserDao) ac.getBean("userDao");
        userDao.test();
    }
}
