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

@RunWith(SpringJUnit4ClassRunner.class) // 将测试运行在Spring测试环境中
@ContextConfiguration(locations = {"classpath:spring.xml"}) // 设置要加载的配置文件
public class SpringJdbcTest03 {

    @Resource
    private JdbcTemplate jdbcTemplate;


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
