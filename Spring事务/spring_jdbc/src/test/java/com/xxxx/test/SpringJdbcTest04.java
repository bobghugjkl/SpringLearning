package com.xxxx.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;


public class SpringJdbcTest04 extends BaseTest {

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
