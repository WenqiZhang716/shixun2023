package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.models.AreaInfo;

import java.util.List;

public interface IAreaInfoService {
    int saveAreaInfo(String areaName,int type,double totalSpace,double remainSpace,double cost,int shelveNum,int shelveLayer);
    List<AreaInfo> findByAreaName(String areaName);
    List<AreaInfo> getAreaInfoList();

    int updateAreaInfo(AreaInfo areaInfo);
    List<AreaInfo> getByAreaType(int type);
    //int changeRemainSpace(String areaName, double newRemainSpace);
    int changeRemainSpaceById(int id, double newRemainSpace);
    int updateAreaInfoForRetrieve(int area_id,double space,int size,int num);
}
