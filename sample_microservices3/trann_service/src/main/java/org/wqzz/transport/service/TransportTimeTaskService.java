//package org.wqzz.transport.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//import org.wqzz.transport.entity.Transport;
//import org.wqzz.transport.repository.TransportRepository;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
///**
// * @author zhangwq
// */
//@Component
//@Service
//public class TransportTimeTaskService {
//
//    @Autowired
//    TransportRepository transportRepository;
//
//    @Autowired
//    private RestTemplate restTemplate;
//
////    @Autowired
////    ManifestRepository manifestRepository;
//
//    //用于模拟上门取件，物流，以及货后付款
//    @Async
//    @Scheduled(initialDelay = 10000,fixedDelay = 15000) //定时任务在方法上的注解
//    public void timedChange0To1(){
//        Optional<Transport> transport = transportRepository.findFirstByStatusAndIsValid(0,1);
//        if(transport.isPresent()){
//            Transport trans=transport.get();
//            transportRepository.updateArriveTimeAndStatusAndTransporterIdById(trans.getId(),new Date(),1,trans.getTransporterId());
//            System.out.println(trans.getManifestId()+" 已到达 "+trans.getStepId());
//
//        }
//    }
//
//    @Async
//    @Scheduled(initialDelay = 15000,fixedDelay = 30000) //定时任务在方法上的注解
//    public void timedChange1To2(){
//        Optional<Transport> transport = transportRepository.findFirstByStatusAndIsValid(1,1);
//        if(transport.isPresent()){
//            Transport trans=transport.get();
//            transportRepository.updateLeaveTimeAndStatus(trans.getId(),new Date(),2);
//            System.out.println(trans.getManifestId() + " 离开 " + trans.getStepId());
//
//        }
//    }
//
//
//    @Async
//    @Scheduled(initialDelay = 20000,fixedDelay = 1000) //定时任务在方法上的注解
//    public void finishManifest(){
//        List<Transport>list=transportRepository.findAllFinish();
//        if(!list.isEmpty()){
//            for (Transport transport:list){
//                restTemplate.getForObject("http://MANIFEST-SERVICE:8003/api/mani/manifest-finish-manifest/"+transport.getManifestId(),Integer.class);
//            }
//        }
//
//    }
//
//    @Async
//    @Scheduled(initialDelay = 20000,fixedDelay = 1000) //定时任务在方法上的注解
//    public void changeOneManifest(){
//        List<Transport>list=transportRepository.findAllChange();
//        if(!list.isEmpty()){
//            for (Transport transport:list){
//                restTemplate.getForObject("http://MANIFEST-SERVICE:8003/api/mani/manifest-change-one-manifest/"+transport.getManifestId(),Integer.class);
//
//            }
//        }
//
//    }
//
//
//}
