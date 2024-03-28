package com.xxxx;

import com.xxxx.config.IocConfig;
import com.xxxx.dao.AccountDao;
import com.xxxx.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class start {
    public static void main(String[] args) {
//        启动类
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(IocConfig.class);
        UserService userService = ac.getBean(UserService.class);
        userService.test();
        IocConfig iocConfig = ac.getBean(IocConfig.class);
//        看是否是单例(也可以获得两个，看地址是否一样，一样则为单例模式)
//        bean对象用于整合第三方bean对象
        System.out.println(ac.isSingleton("iocConfig"));
        AccountDao accountDao = iocConfig.accountDao();

    }
}
