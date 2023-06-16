package com.bezkoder.springjwt.service.impl;

import com.bezkoder.springjwt.models.GoodType;
import com.bezkoder.springjwt.repository.GoodTypeRepository;
import com.bezkoder.springjwt.service.IGoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodsTypeServiceImpl implements IGoodsTypeService {

    @Autowired
    GoodTypeRepository goodTypeRepository;

    @Override
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