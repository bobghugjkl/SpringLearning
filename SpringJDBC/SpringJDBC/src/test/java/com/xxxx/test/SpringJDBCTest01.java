package com.xxxx.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class SpringJDBCTest01 {
    @Test
    public void testJdbc(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        JdbcTemplate jdbcTemplate = (JdbcTemplate) applicationContext.getBean("jdbcTemplate");
        String sql = "select count(1) from tb_account where user_id = ?";
        Integer total = jdbcTemplate.queryForObject(sql,Integer.class,2);
        System.out.println(total);
    }
}
