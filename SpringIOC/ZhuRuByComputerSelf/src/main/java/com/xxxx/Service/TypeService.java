package com.xxxx.Service;

import com.xxxx.Dao.TypeDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class TypeService {
    @Resource
    private TypeDao typeDao;

    public void test(){

        typeDao.test();
        System.out.println("TypeService running...");
    }
}
