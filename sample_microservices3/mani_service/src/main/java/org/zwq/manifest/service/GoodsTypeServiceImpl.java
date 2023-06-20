package org.zwq.manifest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zwq.manifest.entity.GoodType;
import org.zwq.manifest.repository.GoodTypeRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodsTypeServiceImpl{

    @Autowired
    GoodTypeRepository goodTypeRepository;

    public List<Object> getGoodsTypeList() {
        List<GoodType> list=goodTypeRepository.findAll();
        List<Object>goodList=new ArrayList<>();
        for (GoodType good:list){
            Map<String,Object> map=new HashMap<>();
            map.put("value",good.getId());
            map.put("label",good.getName());
            goodList.add(map);
        }
        return goodList;
    }
}