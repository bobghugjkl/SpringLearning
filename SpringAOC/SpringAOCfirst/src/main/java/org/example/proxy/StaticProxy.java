package org.example.proxy;

public class StaticProxy {
    public static void main(String[] args) {


        You you = new You();
        MarryCompany marrycompany = new MarryCompany(you);


        marrycompany.toMarry();
    }
}
