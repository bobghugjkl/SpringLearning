package com.xxxx.service;

import com.xxxx.dao.StudentDao;
import com.xxxx.dao.UserDao;

import java.util.*;

/*
set方法注入
1.属性字段提供set方法
2.在配置文件中通过property属性指定属性字段
 */
public class UserService {
//    手动实例化
//    private UserDao userDao = new UserDao();
//    业务逻辑对象javaBean对象，set方法注入
    private UserDao userDao;
    private StudentDao studentDao;

    public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
//    常用类型（日期类型）
    private String host;

    public void setHost(String host) {
        this.host = host;
    }
//      基本类型
    private Integer port;

    public void setPort(Integer port) {
        this.port = port;
    }
    private List<String> list;

    public void setList(List<String> list) {
        this.list = list;
    }
    public void printlist(){
        list.forEach((v -> System.out.println(v)));
    }
    private Set<String>sets;

    public void setSets(Set<String> sets) {
        this.sets = sets;
    }
    public void printSet(){
        sets.forEach((v-> System.out.println(v)));
    }
    private Map<String, String>map;

    public void setMap(Map<String, String> map) {
        this.map = map;
    }



    public void printMap() {
        map.forEach((k,v)-> System.out.println(k+" "+v));
    }
    private Properties properties;

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
    public void printPro(){
        properties.forEach((k,v)-> System.out.println(k+"="+v));
    }
    public void test(){
        System.out.println("Running....");
        userDao.test();
        studentDao.test();
//        常用类型
        System.out.println(host);
        System.out.println(port);
        printlist();
        printMap();
        printSet();
        printPro();
    }
}
