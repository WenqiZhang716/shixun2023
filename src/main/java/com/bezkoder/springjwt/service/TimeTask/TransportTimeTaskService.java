package com.bezkoder.springjwt.service.TimeTask;

import com.bezkoder.springjwt.models.Manifest;
import com.bezkoder.springjwt.models.Transport;
import com.bezkoder.springjwt.repository.ManifestRepository;
import com.bezkoder.springjwt.repository.TransportRepository;
import com.bezkoder.springjwt.service.ITransporterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangwq
 */
@Component
@Service
public class TransportTimeTaskService {

    @Autowired
    TransportRepository transportRepository;

    @Autowired
    ManifestRepository manifestRepository;

    //用于模拟上门取件，物流，以及货后付款
    @Async
    @Scheduled(initialDelay = 10000,fixedDelay = 15000) //定时任务在方法上的注解
    public void timedChange0To1(){
        Optional<Transport> transport = transportRepository.findFirstByStatusAndIsValid(0,1);
        if(transport.isPresent()){
            Transport trans=transport.get();
            transportRepository.updateArriveTimeAndStatusAndTransporterIdById(trans.getId(),new Date(),1,trans.getTransporterId());
            System.out.println(trans.getManifestId()+" 已到达 "+trans.getStepId());

        }
    }

    @Async
    @Scheduled(initialDelay = 15000,fixedDelay = 30000) //定时任务在方法上的注解
    public void timedChange1To2(){
        Optional<Transport> transport = transportRepository.findFirstByStatusAndIsValid(1,1);
        if(transport.isPresent()){
            Transport trans=transport.get();
            transportRepository.updateLeaveTimeAndStatus(trans.getId(),new Date(),2);
            System.out.println(trans.getManifestId() + " 离开 " + trans.getStepId());

        }
    }


    @Async
    @Scheduled(initialDelay = 20000,fixedDelay = 1000) //定时任务在方法上的注解
    public void finishManifest(){
        List<Transport>list=transportRepository.findAllFinish();
        if(!list.isEmpty()){
            for (Transport transport:list){
                Optional<Manifest>mani=manifestRepository.findById(transport.getManifestId());
                if(mani.isPresent()){
                    Manifest manifest = mani.get();
                    if(manifest.getStatus()==1){
                        manifestRepository.updateStatusByManiId(manifest.getId(),2);
                        manifestRepository.updateIsPay(manifest.getId());
                        System.out.println("完成一条订单");
                    }

                }
            }
        }

    }

    @Async
    @Scheduled(initialDelay = 20000,fixedDelay = 1000) //定时任务在方法上的注解
    public void changeOneManifest(){
        List<Transport>list=transportRepository.findAllChange();
        if(!list.isEmpty()){
            for (Transport transport:list){
                Optional<Manifest>mani=manifestRepository.findById(transport.getManifestId());
                if(mani.isPresent()){
                    Manifest manifest = mani.get();
                    //修改成进行中
                    if(manifest.getStatus()==0){
                        manifestRepository.updateStatusByManiId(manifest.getId(),1);
                        System.out.println("将一条订单状态修改为进行中");
                    }

                }
            }
        }

    }


}
