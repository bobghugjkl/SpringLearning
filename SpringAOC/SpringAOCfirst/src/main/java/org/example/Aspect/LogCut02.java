package org.example.Aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component

public class LogCut02 {
    public void cut(){
//        切入点，定义要拦截哪些类的哪些方法，匹配规则拦截什么方法

    }
//    通知

    public void before(){
        System.out.println("information before");
    }

    public void afterReturn(){
        System.out.println("information return");
    }

    public void after(){
        System.out.println("information after");
    }

    public void afterThrow(Exception e){
        System.out.println("information after throw"+ e.getMessage());
    }

    public Object around(ProceedingJoinPoint pjp){
        System.out.println("information around");
        Object object = null;
        try{
            object = pjp.proceed();
            System.out.println(pjp.getTarget());
        }catch (Throwable throwable){
            throwable.printStackTrace();
            System.out.println("there is throw");
        }
        System.out.println("end");
        return object;
    }
}
