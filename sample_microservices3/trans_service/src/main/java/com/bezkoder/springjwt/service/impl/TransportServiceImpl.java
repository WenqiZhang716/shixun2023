package com.bezkoder.springjwt.service.impl;

import com.bezkoder.springjwt.models.Manifest;
import com.bezkoder.springjwt.models.Transport;
import com.bezkoder.springjwt.models.TransportStep;
import com.bezkoder.springjwt.repository.ManifestRepository;
import com.bezkoder.springjwt.repository.TransportRepository;
import com.bezkoder.springjwt.repository.TransportStepRepository;
import com.bezkoder.springjwt.service.ITransportService;
import com.bezkoder.springjwt.service.ITransportStepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author zhangwq
 */
@Service
public class TransportServiceImpl implements ITransportService {
    @Autowired
    TransportStepRepository transportStepRepository;
    @Autowired
    TransportRepository transportRepository;
    @Autowired
    ManifestRepository manifestRepository;
    @Autowired
    ITransportStepService transportStepService;

    @Override
    public List<Transport> pathPlan(int manifestId) {
        Optional<Manifest> manifest = manifestRepository.findById(manifestId);
        List<TransportStep>list=new ArrayList<>();
        if(manifest.isPresent()){
            Manifest mani=manifest.get();
            String beginA=mani.getBeginAddress();
            String endA=mani.getEndAddress();
            List<TransportStep> beginList =transportStepService.getStepIdByString(beginA);
            List<TransportStep>endList=transportStepService.getStepIdByString(endA);
            int len1=beginList.size();
            int len2=endList.size();
            for(int i=len1-1;i>=0;i--){
                list.add(beginList.get(i));
            }
            for (TransportStep transportStep : endList) {
                if (list.contains(transportStep)) {
                    list.remove(transportStep);
                } else {
                    list.add(transportStep);
                }
            }
            CreateOne(mani.getId(),mani.getBeginAddress(),0,0,1,1);
            int o=1;
            for (TransportStep transportStep : list) {
                o++;
               CreateOne(mani.getId(),transportStep.getName()+"中转站",transportStep.getId(),0,0,o);
            }
            CreateOne(mani.getId(),mani.getEndAddress(),0,0,1,++o);
            //先付后到，默认直接进入定时刷新状态
            if(mani.getPayType()==1){
                transportRepository.updateValidByManifestId(mani.getId());
            }
        }
        List<Transport>tList=getPathList(manifestId);
        int beginId=tList.get(1).getStepId();
        int endId=tList.get(tList.size()-2).getStepId();
        manifestRepository.updateBeginAndEndId(manifestId,beginId,endId);


        return tList;
    }

    @Override
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

    @Override
    public int deleteAll(int manifestId) {
        transportRepository.deleteAllByManifestId(manifestId);
        return 0;
    }

    @Override
    public List<Transport> getPathList(int manifestId) {
        List<Transport>tList=new ArrayList<>();
        tList=transportRepository.findAllByManifestId(manifestId);
        return tList;
    }

    @Override
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