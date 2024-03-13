package org.example.proxy;

public class CiglibInterceptor {
    public static void main(String[] args) {
        You you = new You();
        CglibIntercepter cglibIntercepter = new CglibIntercepter(you);
        Marry marry =(Marry) cglibIntercepter.getProxy();
        marry.toMarry();



    }
}
