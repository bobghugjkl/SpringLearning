package org.example.proxy;

public class Test {
    public static void main(String[] args) {
        You you = new You();
        JdkHanlder jdkHanlder = new JdkHanlder(you);
        Marry marry = (Marry) jdkHanlder.getProxy();
        marry.toMarry();
    }
}
