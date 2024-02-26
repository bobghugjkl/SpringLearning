package com.xxxx.factory;

import com.xxxx.dao.TypeDao;

//静态工厂注入
/*
静态工厂实例化基本一样
 */
public class StaticFactory {
//    定义静态方法,目的就是创建一个typedao
    public static TypeDao createTypeDao(){
        return new TypeDao();
    }
}
