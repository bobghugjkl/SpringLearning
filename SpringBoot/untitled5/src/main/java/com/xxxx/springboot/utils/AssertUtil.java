package com.xxxx.springboot.utils;

import com.mysql.cj.exceptions.SSLParamsException;
import com.sun.org.apache.xpath.internal.operations.Bool;

public class AssertUtil {
    public static void isTrue(Boolean flag,String msg){
        if(flag){
            throw new SSLParamsException(msg);
        }
    }
}
