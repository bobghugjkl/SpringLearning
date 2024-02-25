package com.test;

import com.xxxx.Dao.UserDao;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Start03 {
    public static void main(String[] args) {
        BeanFactory factory = new ClassPathXmlApplicationContext("service.xml");
        UserDao userDao = (UserDao) factory.getBean("userDao");
        userDao.test();
    }
}
