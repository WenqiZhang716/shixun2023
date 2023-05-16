package com.bezkoder.springjwt.service.impl;

import com.bezkoder.springjwt.models.StoreInfo;
import com.bezkoder.springjwt.repository.StoreInfoRepository;
import com.bezkoder.springjwt.service.IStoreInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreInfoServiceImpl implements IStoreInfoService {

    @Autowired(required = false)
    private StoreInfoRepository storeInfoRepository;
    @Override
    public int saveStoreInfo(StoreInfo storeInfo) {

        storeInfoRepository.save(storeInfo);
        return 0;
    }

    @Override
    public List<StoreInfo> getStoreInfoList() {
        List<StoreInfo> list=storeInfoRepository.findAll();
        return list;
    }

    @Override
    public List<StoreInfo> findStoreInfoByGoodsId(int goodsId) {
        List<StoreInfo> list=storeInfoRepository.findByGoodsId(goodsId);
        return list;
    }

    @Override
    public List<StoreInfo> findByAreaAndShelveX(int areaId, int shelveX) {
        List<StoreInfo> list = storeInfoRepository.findByAreaIdAndShelveX(areaId,shelveX);
        return list;
    }

    @Override
    public int deleteStoreInfoByGoodsId(int goodsId) {
        int d=storeInfoRepository.deleteByGoodsId(goodsId);
        return d;
    }

    @Override
    public int changeTime() {
        int d=storeInfoRepository.updateTime();
        return d;
    }

    @Override
    public List<StoreInfo> limitFindStoreInfoByGoodsId(int goodsId, int num) {
        List<StoreInfo> list=storeInfoRepository.limitFindByGoodsId(goodsId,num);
        return list;
    }

    @Override
    public int deleteStoreInfoById(int id) {
        int d=storeInfoRepository.deleteById(id);
        return d;
    }

    @Override
    public List<StoreInfo> findStoreInfoByApplyId(int applyId) {
        List<StoreInfo> list=storeInfoRepository.findByApplyId(applyId);
        return list;
    }
}
