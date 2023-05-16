package com.bezkoder.springjwt.service.impl;

import com.bezkoder.springjwt.models.GoodsType;
import com.bezkoder.springjwt.repository.GoodsTypeRepository;
import com.bezkoder.springjwt.service.IGoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GoodsTypeServiceImpl implements IGoodsTypeService {
    @Autowired
    GoodsTypeRepository goodsTypeRepository;

    @Override
    public List<GoodsType> getList() {
        return goodsTypeRepository.findAll();
    }

    @Override
    public int saveGoodsType(String name) {
        GoodsType goodsType = new GoodsType();
        goodsType.setName(name);
        goodsTypeRepository.save(goodsType);
        return 0;
    }

    @Override
    public GoodsType getById(int id) {
      return   goodsTypeRepository.findById(id);
    }
}
