package com.bezkoder.springjwt.service.TimeTask;

import com.bezkoder.springjwt.repository.TransportRepository;
import com.bezkoder.springjwt.service.ITransporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangwq
 */
@Component
public class TransportTimeTaskService {

//    @Async
//    @Scheduled(fixedDelay = 5000) //定时任务在方法上的注解
//    public void timedB(){
//        System.out.println("A 小熊369  "+new Date());
//    }
    


}
