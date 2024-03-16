package com.xxxx.test;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class) // 将测试运行在Spring测试环境中
@ContextConfiguration(locations = {"classpath:spring.xml"}) // 设置要加载的配置文件
public class BaseTest {
}
