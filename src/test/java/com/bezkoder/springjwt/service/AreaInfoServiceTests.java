package com.bezkoder.springjwt.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AreaInfoServiceTests {
    @Autowired(required = false)
    private IAreaInfoService areaInfoService;

    @Test
    public void saveAreaInfo(){
        String areaName="零件C区";
        int type = 3;
        double totalSpace = 110;
        double remainSpace = 110;
        double cost = 10;
        int shelveNum = 5;
        int shelveLayer = 5;
        areaInfoService.saveAreaInfo(areaName,type,totalSpace,remainSpace,cost,shelveNum,shelveLayer);
    }
//    @Test
//    public void changeRemainSpace(){
//        String areaName="零件A区";
//        double newRemainSpace=10;
//        areaInfoService.changeRemainSpace(areaName,newRemainSpace);
//    }
    @Test
    public void findByAreaName(){
        String areaName="零件A区";
        areaInfoService.findByAreaName(areaName);
    }
    @Test
    public void getAreaInfoList(){
        areaInfoService.getAreaInfoList();
    }

}
