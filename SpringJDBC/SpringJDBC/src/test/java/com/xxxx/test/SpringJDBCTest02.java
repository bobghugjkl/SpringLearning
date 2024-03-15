package com.xxxx.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class SpringJDBCTest02 {
    private JdbcTemplate jdbcTemplate;
    @Before
    public void init(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        jdbcTemplate = (JdbcTemplate) applicationContext.getBean("jdbcTemplate");
    }
    @Test
    public void testJdbc(){

        String sql = "select count(1) from tb_account where user_id = ?";
        Integer total = jdbcTemplate.queryForObject(sql,Integer.class,2);
        System.out.println(total);
    }
}
