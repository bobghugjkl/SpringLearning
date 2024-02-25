package org.example;

import org.example.Spring.MyClassPathXmlApplicationContext;
import org.example.Spring.MyFactory;
import org.example.Srevice.UserService;
import org.example.dao.UserDao;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
//        得到对应工厂的实现对象
        MyFactory factory = new MyClassPathXmlApplicationContext("spring.xml");
//        得到对应的实例化对象
        UserDao userDao = (UserDao) factory.getBean("userDao");
        userDao.test();
        UserService userService = (UserService) factory.getBean("userService");
        userService.test();

    }
}
