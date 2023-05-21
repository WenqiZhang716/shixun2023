package com.bezkoder.springjwt.service.impl;

import com.bezkoder.springjwt.models.TransportStep;
import com.bezkoder.springjwt.repository.TransportStepRepository;
import com.bezkoder.springjwt.service.ITransportStepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}