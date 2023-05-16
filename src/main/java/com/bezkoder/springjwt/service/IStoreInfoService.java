package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.models.AreaType;
import com.bezkoder.springjwt.models.StoreInfo;

import java.util.List;

public interface IStoreInfoService {
    int saveStoreInfo(StoreInfo storeInfo);
    List<StoreInfo> getStoreInfoList();
    List<StoreInfo> findStoreInfoByGoodsId(int goodsId);
    List<StoreInfo> findByAreaAndShelveX(int areaId,int shelveX);
    int deleteStoreInfoByGoodsId(int goodsId);
    int changeTime();

    List<StoreInfo> limitFindStoreInfoByGoodsId(int goodsId,int num);

    int deleteStoreInfoById(int id);

    List<StoreInfo> findStoreInfoByApplyId(int applyId);

}
