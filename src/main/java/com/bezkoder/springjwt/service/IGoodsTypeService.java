package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.models.GoodsType;

import java.util.List;

public interface IGoodsTypeService {
    List<GoodsType> getList();
    int saveGoodsType(String name);

    GoodsType getById(int id);
}
