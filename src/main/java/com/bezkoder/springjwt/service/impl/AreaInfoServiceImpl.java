package com.bezkoder.springjwt.service.impl;

import com.bezkoder.springjwt.models.AreaInfo;
import com.bezkoder.springjwt.repository.AreaInfoRepository;
import com.bezkoder.springjwt.service.IAreaInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaInfoServiceImpl implements IAreaInfoService {

    @Autowired(required = false)
    private AreaInfoRepository areaInfoRepository;

    @Override
    public int saveAreaInfo(String areaName, int type, double totalSpace, double remainSpace, double cost, int shelveNum, int shelveLayer) {
        AreaInfo areaInfo =new AreaInfo();
        areaInfo.setAreaName(areaName);
        areaInfo.setTotalSpace(totalSpace);
        areaInfo.setRemainSpace(remainSpace);
        areaInfo.setCost(cost);
        areaInfo.setShelveNum(shelveNum);
        areaInfo.setShelveLayer(shelveLayer);
        List<AreaInfo> list =areaInfoRepository.findByAreaName(areaName);
        if(list!=null&& !list.isEmpty()){
            return 0;
        }else{
            areaInfoRepository.save(areaInfo);
            return 1;
        }
    }

    @Override
    public List<AreaInfo> findByAreaName(String areaName) {
        List<AreaInfo> list=areaInfoRepository.findByAreaName(areaName);
        return list;
    }

    @Override
    public List<AreaInfo> getAreaInfoList() {
        List<AreaInfo> list=areaInfoRepository.findAll();
        return list;
    }

    @Override
    public int updateAreaInfo(AreaInfo areaInfo) {
        areaInfoRepository.saveAndFlush(areaInfo);
        return 0;
    }

    @Override
    public List<AreaInfo> getByAreaType(int type) {
        return areaInfoRepository.findByType(type);
    }

    /*@Override
    public int changeRemainSpace(String areaName, double newRemainSpace) {
        int res=areaInfoRepository.updateRemainSpace(areaName,newRemainSpace);
        return res;
    }*/
    @Override
    public int changeRemainSpaceById(int id, double newRemainSpace){
        int res=areaInfoRepository.updateRemainSpace(id,newRemainSpace);
        return res;
    }

    @Override
    public int updateAreaInfoForRetrieve(int area_id, double space, int size, int num) {
        areaInfoRepository.updateRemainSpace(area_id,space*num);
        switch (size){
            case 1:
                areaInfoRepository.updateRemain1(area_id,num);
                break;
            case 2:
                areaInfoRepository.updateRemain2(area_id,num);
                break;
            case 3:
                areaInfoRepository.updateRemain3(area_id,num);
                break;
            case 4:
                areaInfoRepository.updateRemain4(area_id,num);
                break;
            default:
                break;
        }

        return 1;
    }
}
