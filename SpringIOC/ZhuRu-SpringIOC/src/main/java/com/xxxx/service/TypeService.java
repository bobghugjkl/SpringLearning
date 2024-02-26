package com.xxxx.service;

import com.xxxx.dao.TypeDao;

public class TypeService {
//    注入typedao
    private TypeDao typeDao;

    public void setTypeDao(TypeDao typeDao) {
        this.typeDao = typeDao;
    }
    public void test(){
        System.out.println("TypeService Running...");
    }
}
