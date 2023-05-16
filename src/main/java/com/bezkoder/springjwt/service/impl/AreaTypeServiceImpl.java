package com.bezkoder.springjwt.service.impl;

import com.bezkoder.springjwt.models.AreaType;
import com.bezkoder.springjwt.repository.AreaTypeRepository;
import com.bezkoder.springjwt.service.IAreaTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaTypeServiceImpl implements IAreaTypeService {

    @Autowired(required = false)
    private AreaTypeRepository areaTypeRepository;

    @Override
    public int saveAreaType(String name) {
        AreaType areaType = new AreaType();
        areaType.setName(name);
        List<AreaType> list =areaTypeRepository.findByName(name);
        if(list!=null&& !list.isEmpty()){
            return 0;
        }else{
            areaTypeRepository.save(areaType);
            return 1;
        }
    }

    @Override
    public AreaType findAreaType(String name) {
        List<AreaType> list=areaTypeRepository.findByName(name);
        return list.get(0);
    }

    @Override
    public List<AreaType> getAreaTypeList() {
        List<AreaType> list=areaTypeRepository.findAll();
        return list;
    }

    @Override
    public int deleteAreaTypeByName(String name) {
        int d=areaTypeRepository.deleteByName(name);
        return d;
    }

    @Override
    public int changeName(String name, String newName) {
        int a=areaTypeRepository.updateName(name,newName);
        return a;
    }
}
