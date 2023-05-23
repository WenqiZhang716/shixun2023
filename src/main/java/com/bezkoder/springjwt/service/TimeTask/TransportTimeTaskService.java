package com.bezkoder.springjwt.service.TimeTask;

import com.bezkoder.springjwt.repository.TransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author zhangwq
 */
@Component
public class TransportTimeTaskService {
    @Autowired
    TransportRepository transportRepository;

    @Scheduled(initialDelay = 1000,fixedRate = 30000) //定时任务在方法上的注解
    public void timedA(){
        System.out.println("A 小熊369  "+new Date());
    }


}
