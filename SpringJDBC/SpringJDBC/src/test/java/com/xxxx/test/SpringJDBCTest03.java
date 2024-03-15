package com.xxxx.test;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class SpringJDBCTest03 {

    @Resource
    private JdbcTemplate jdbcTemplate;
    @Test
    public void testJdbc(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        jdbcTemplate = (JdbcTemplate) applicationContext.getBean("jdbcTemplate");
        String sql = "select count(1) from tb_account where user_id = ?";
        Integer total = jdbcTemplate.queryForObject(sql,Integer.class,2);
        System.out.println(total);
    }
}
