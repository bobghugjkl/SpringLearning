package com.xxxx;

import com.xxxx.Controller.TypeController;
import com.xxxx.Dao.TypeDao;
import com.xxxx.Service.AccountService;
import com.xxxx.Service.TypeService;
import com.xxxx.Service.UserService;
import com.xxxx.utils.PropertyUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Properties;

/**
 * Hello world!
 *
 */
public class App02
{
    public static void main( String[] args )
    {
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring02.xml");
        TypeDao typeDao = (TypeDao) ac.getBean("typeDao");
        typeDao.test();
        TypeService typeService = (TypeService) ac.getBean("typeService");
        typeService.test();
        TypeController typeController = (TypeController) ac.getBean("typeController");
        typeController.test();
        PropertyUtils propertyUtils = (PropertyUtils) ac.getBean("propertyUtils");
        propertyUtils.test();
    }
}
