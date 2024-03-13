package org.example.Aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogCut {
    @Pointcut("execution(* org.example.Service..*.*(..))")
    public void cut(){
//        切入点，定义要拦截哪些类的哪些方法，匹配规则拦截什么方法

    }
//    通知
    @Before(value = "cut()")
    public void before(){
        System.out.println("information before");
    }
    @AfterReturning
    public void afterReturn(){
        System.out.println("information return");
    }
    @After(value = "cut()")
    public void after(){
        System.out.println("information after");
    }
    @AfterThrowing
    public void afterThrow(){
        System.out.println("information after throw");
    }
    @Around(value = "cut()")
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
