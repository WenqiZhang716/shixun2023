package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.models.GoodsInfo;

import java.util.List;

public interface IGoodsInfoService {
    List<GoodsInfo> getList();
    int getType(int id);
    int getSize(int id);
    String getName(int id);
    GoodsInfo findById(int id);

    int saveGoodsInfo(GoodsInfo goodsInfo);

    int changeSpace(Integer id, int newSize);
    int changeSum(Integer id, int newSum);

    int updateSum(Integer id,int num);
}
