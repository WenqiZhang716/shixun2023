package com.bezkoder.springjwt.service.impl;

import com.bezkoder.springjwt.models.GoodsInfo;
import com.bezkoder.springjwt.repository.GoodsInfoRepository;
import com.bezkoder.springjwt.service.IGoodsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsInfoServiceImpl implements IGoodsInfoService {

    @Autowired
    GoodsInfoRepository goodsInfoRepository;

    @Override
    public List<GoodsInfo> getList() {
        return goodsInfoRepository.findAll();
    }

    @Override
    public int getType(int id) {
        GoodsInfo goodsInfo=goodsInfoRepository.findById(id);
        return goodsInfo.getType();
    }

    @Override
    public int getSize(int id) {
        GoodsInfo goodsInfo=goodsInfoRepository.findById(id);
        return goodsInfo.getSize();
    }

    @Override
    public String getName(int id) {
        GoodsInfo goodsInfo=goodsInfoRepository.findById(id);
        return goodsInfo.getName();
    }

    @Override
    public GoodsInfo findById(int id){return goodsInfoRepository.findById(id);}

    @Override
    public int saveGoodsInfo(GoodsInfo goodsInfo){
        goodsInfoRepository.saveAndFlush(goodsInfo);
        return 0;
    }

//    @Override
//    public int deleteByOwnerId(int owner_id) {
//        int n = goodsInfoRepository.deleteByOwnerId(owner_id);
//        return n;
//    }

    @Override
    public int changeSpace(Integer id, int newSize) {

        GoodsInfo goodsInfo = goodsInfoRepository.findById(id);
        goodsInfo.setSize(newSize);
        goodsInfoRepository.save(goodsInfo);
        return 1;
    }

    @Override
    public int changeSum(Integer id, int newSum) {
        GoodsInfo goodsInfo = goodsInfoRepository.findById(id);
        goodsInfo.setSum(newSum);
        goodsInfoRepository.save(goodsInfo);
        return 1;
    }

    @Override
    public int updateSum(Integer id, int num) {

        GoodsInfo goodsInfo = goodsInfoRepository.findById(id);
        goodsInfo.setSum(goodsInfo.getSum()+num);

        goodsInfoRepository.save(goodsInfo);
        return 0;
    }

}
