package com.xxxx.utils;

public class StringUtils {
    public static boolean isEmpty(String str){
        if(str == null||"".equals(str.trim())){
            return true;
        }
        return false;
    }
}
