package com.xxxx.config;

import com.xxxx.dao.AccountDao;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

//配置类
@Configurable
@ComponentScan("com.xxxx")
@PropertySource(value = {"classpath:jdbc.properties","classpath:user.properties"})
public class IocConfig {
    @Value("${jdbc.driver}")
    private String url;
    @Value("${jdbc.url}")
    private String driver;
    @Value("${jdbc.username}")
    private String userName;
    @Value("${jdbc.password}")
    private String password;
    @Bean
    public AccountDao accountDao(){
        return new AccountDao();
    }
    public void printInfo(){
        System.out.println(url + driver + userName + password);
    }
}
