package com.xxxx.factory;

import com.xxxx.dao.TypeDao;
//实例化工厂与静态工厂就差一个static
public class InstanceFactory {
    public TypeDao typeDao(){
        return new TypeDao();
    }
}
