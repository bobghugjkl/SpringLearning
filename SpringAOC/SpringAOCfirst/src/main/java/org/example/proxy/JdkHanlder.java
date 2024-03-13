package org.example.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Properties;

public class JdkHanlder implements InvocationHandler {

    private Object target; //目标对象类型不固定，动态生产

    public JdkHanlder(Object target) {
        this.target = target;
    }

    public Object getProxy(){
        Object object = Proxy.newProxyInstance(this.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
        return object;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println(proxy.getClass().getName());
//        增强
        System.out.println("斋藤飞鸟");
//        调用
        Object object = method.invoke(target,args);
        return object;
    }
}
