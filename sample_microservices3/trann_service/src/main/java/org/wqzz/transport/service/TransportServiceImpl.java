package org.wqzz.transport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wqzz.transport.entity.Transport;
import org.wqzz.transport.repository.TransportRepository;
import org.wqzz.transport.repository.TransportStepRepository;

import java.util.*;

/**
 * @author zhangwq
 */
@Service
public class TransportServiceImpl{
    @Autowired
    TransportStepRepository transportStepRepository;
    @Autowired
    TransportRepository transportRepository;
//    @Autowired
//    ManifestRepository manifestRepository;
    @Autowired
    TransportStepServiceImpl transportStepService;




    public int CreateOne(int manifestId, String step,int stepId, int transporterId, int type,int orders) {
        try{
            Transport trans=new Transport();
            trans.setManifestId(manifestId);
            trans.setStep(step);
            trans.setTransporterId(transporterId);
            trans.setType(type);
            trans.setOrders(orders);
            trans.setStepId(stepId);
            transportRepository.save(trans);
            return 1;
        }catch(Exception e){
            return 0;
        }


    }


    public int deleteAll(int manifestId) {
        transportRepository.deleteAllByManifestId(manifestId);
        return 0;
    }


    public List<Transport> getPathList(int manifestId) {
        List<Transport>tList=new ArrayList<>();
        tList=transportRepository.findAllByManifestId(manifestId);
        return tList;
    }


    public Map<String, Object> getStepInfo(int manifestId) {
        List<Transport>list=getPathList(manifestId);
        List<Object> list2=new ArrayList<>();
        int flag=0;
        int i=0;
        for(Transport t:list){
            i++;
            Map<String,Object>map=new HashMap<>();
            map.put("title",t.getOrders()+":"+t.getStep());
            String des="";
            if(t.getStatus()==0){
                des="未抵达";
            }else if(t.getStatus()==1){
                des="已抵达"+"\n"+"抵达时间: "+t.getArriveTime();
            }else if(t.getStatus()==2){
                des="已完成中转"+"\n"+"抵达时间: "+t.getArriveTime();
                if(!(t.getOrders()>1&&t.getStepId()==0)){
                    des=des+"\n"+"离开时间 :"+t.getLeaveTime();
                }
                flag=i;
            }
            map.put("desc",des);
            list2.add(map);
        }
        HashMap<String, Object> mapp=new HashMap<>();
        mapp.put("path",list2);
        mapp.put("status",flag);

        return mapp;
    }
}