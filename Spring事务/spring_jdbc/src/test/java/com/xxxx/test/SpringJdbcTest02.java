package com.xxxx.test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class SpringJdbcTest02 {

    private JdbcTemplate jdbcTemplate;

    @Before
    public void init(){
        // 获取Spring的上下文环境
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
        // 得到模板类 JdbcTemplate对象
        jdbcTemplate = (JdbcTemplate) ac.getBean("jdbcTemplate");
    }

    @Test
    public void testJdbc(){

        // crud 操作
        // 定义sql语句
        String sql = "select count(1) from tb_account";
        // 执行查询操作 （无参数）
        Integer total = jdbcTemplate.queryForObject(sql,Integer.class);

        System.out.println("总记录数：" + total);
    }

    @Test
    public void testJdbc02(){

        // crud 操作
        // 定义sql语句
        String sql = "select count(1) from tb_account where user_id = ?";
        // 执行查询操作 （无参数）
        Integer total = jdbcTemplate.queryForObject(sql,Integer.class,2);

        System.out.println("总记录数：" + total);
    }

}
