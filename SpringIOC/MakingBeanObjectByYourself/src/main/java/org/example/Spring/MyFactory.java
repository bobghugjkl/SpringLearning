package org.example.Spring;
//目的是一个规范，Bean对象的工厂接口定义
public interface MyFactory {
//    通过id属性值获取对象
    public Object getBean(String id);
}
