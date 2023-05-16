package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.models.StoreInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class StoreInfoServiceTests {
    /*int saveStoreInfo(int goodsId,int areaId,double space,int shelveX,int shelveY,int time,double fee);
    List<StoreInfo> getStoreInfoList();
    List<StoreInfo> findStoreInfoByGoodsId(int goodsId);
    int deleteStoreInfoByGoodsId(int goodsId);
    int changeTime();*/
    @Autowired(required = false)
    private IStoreInfoService storeInfoService;

//    @Test
//    public void saveStoreInfo(){
//        int goodsId=1;
//        int areaId=1;
//        double space=9;
//        int shelveX=1;
//        int shelveY=2;
//        int time=10;
//        double fee=10.5;
//        storeInfoService.saveStoreInfo(goodsId,areaId,space,shelveX,shelveY,time,fee);
//    }
    @Test
    public void getStoreInfoList(){
        storeInfoService.getStoreInfoList();
    }
    @Test
    public void findStoreInfoByGoodsId(){
        int goodsId=1;
        storeInfoService.findStoreInfoByGoodsId(goodsId);
    }
    @Test
    public void deleteStoreInfoByGoodsId(){
        int goodsId=1;
        storeInfoService.deleteStoreInfoByGoodsId(goodsId);
    }
    @Test
    public void changeTime(){
        storeInfoService.changeTime();
    }
}
