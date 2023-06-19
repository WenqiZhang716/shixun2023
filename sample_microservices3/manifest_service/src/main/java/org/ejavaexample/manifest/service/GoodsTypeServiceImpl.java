package org.ejavaexample.manifest.service;

import org.ejavaexample.manifest.entity.GoodType;
import org.ejavaexample.manifest.repository.GoodTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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