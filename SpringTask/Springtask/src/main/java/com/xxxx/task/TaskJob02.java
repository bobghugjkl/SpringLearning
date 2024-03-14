package com.xxxx.task;

import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskJob02 {
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Scheduled(cron = "0/2 * * * * ?")
    public void job3(){
        System.out.println("job 3 run"+df.format(new Date()));
    }
}
