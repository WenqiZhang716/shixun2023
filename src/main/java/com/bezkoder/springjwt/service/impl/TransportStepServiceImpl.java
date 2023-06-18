package com.bezkoder.springjwt.service.impl;

import com.bezkoder.springjwt.models.TransportStep;
import com.bezkoder.springjwt.repository.TransportStepRepository;
import com.bezkoder.springjwt.service.ITransportStepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author zhangwq
 */
@Service
public class TransportStepServiceImpl implements ITransportStepService {

    @Autowired
    TransportStepRepository transportStepRepository;
    @Override
    public List<TransportStep> getStepIdByString(String address) {
        String[] sourceStrArray = address.split("_");
        String str=sourceStrArray[0];
        List<TransportStep>list=transportStepRepository.findAllByProvincesLike("%"+str+"%");
        for(int i=2;i<list.size();i++){
            if(!list.get(i).getName().equals(sourceStrArray[1])){
                list.remove(i);
            }
        }
        return list;
    }

    @Override
    public List<Object> getBigStepList() {
        List<TransportStep>list=transportStepRepository.findAllByType(1);
        List<Object>list2=new ArrayList<>();
        for(TransportStep s:list){
            Map<String,Object> map=new HashMap<>();
            map.put("value",s.getId());
            map.put("label",s.getName());
            list2.add(map);
        }
        return list2;
    }

    @Override
    public List<Object> getSmallList(int id) {
        Optional<TransportStep>transportStep=transportStepRepository.findOneById(id);
        List<Object>list2=new ArrayList<>();
        if(transportStep.isPresent()){
            TransportStep step=transportStep.get();
            List<TransportStep>list=transportStepRepository.findAllByTypeAndProvinces(2,step.getName());
            if(list.isEmpty()){
                Map<String,Object> map=new HashMap<>();
                map.put("value",step.getId());
                map.put("label",step.getName());
                list2.add(map);
            }else{
                for(TransportStep s:list){
                    Map<String,Object> map=new HashMap<>();
                    map.put("value",s.getId());
                    map.put("label",s.getName());
                    list2.add(map);
                }
            }
            return list2;
        }
        return null;
    }
}