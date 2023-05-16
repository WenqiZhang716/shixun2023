package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.models.ApplyGoods;

import java.util.List;

public interface IApplyGoodsService {
    List<ApplyGoods> getList();
    List<ApplyGoods> getByApplyId(int apply_id);

    int saveApplyGoods(int apply_id, int store_id);
}
