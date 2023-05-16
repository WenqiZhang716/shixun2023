package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.models.AreaType;

import java.util.List;

public interface IAreaTypeService {
    int saveAreaType(String name);
    AreaType findAreaType(String name);
    List<AreaType> getAreaTypeList();
    int deleteAreaTypeByName(String name);
    int changeName(String name, String newName);
}
