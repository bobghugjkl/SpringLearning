package com.xxxx;

import com.xxxx.task.TaskJob;
import com.xxxx.task.TaskJob02;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring.xml");
//        TaskJob taskJob = (TaskJob)ac.getBean("taskJob");
//        taskJob.job1();
//        System.out.println( "Hello World!" );
        TaskJob02 taskJob02 = (TaskJob02) ac.getBean("taskJob02");
        taskJob02.job3();

    }
}
