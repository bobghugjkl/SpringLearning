package com.xxxx.springsecurity;

public class test {
    public static void main(String[] args) {
        DailyUser dailyUser = new DailyUser("lrs","1",123456,12);
        dailyUser.say();
        System.out.println(dailyUser.getUser()+" "+dailyUser.getUsername()+" "+dailyUser.getAge()+" "+dailyUser.getPassword());
        DailyUser speak = new DailyUser("admin","123",123,12);

    }
}
