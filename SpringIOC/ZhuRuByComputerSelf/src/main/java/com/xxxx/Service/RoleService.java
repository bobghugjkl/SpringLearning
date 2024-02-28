package com.xxxx.Service;

import org.springframework.beans.factory.InitializingBean;

public class RoleService implements InitializingBean {
    public RoleService() {
        System.out.println("空构造");
    }

    public void test(){
        System.out.println("roleservice running");
    }
//    public void init(){
//        System.out.println("init running");
//    }

    public void afterPropertiesSet() throws Exception {
        System.out.println("RoleService init...");
    }
    public void destroy(){
        System.out.println("销毁");
    }
}
