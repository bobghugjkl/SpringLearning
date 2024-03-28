package com.xxxx.springboot;

import com.xxxx.springboot.config.MvcConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
            AnnotationConfigWebApplicationContext ctx=new AnnotationConfigWebApplicationContext();
            // 注册Mvc 配置信息
            ctx.register(MvcConfig.class);
            // 设置ServletContext 上下文信息
            ctx.setServletContext(servletContext);
            // 配置转发器Dispatcher
            ServletRegistration.Dynamic servlet=servletContext.addServlet("dispatcher",new DispatcherServlet(ctx));
            servlet.addMapping("/");
            // 启动时即实例化Bean
            servlet.setLoadOnStartup(1);
    }
}
