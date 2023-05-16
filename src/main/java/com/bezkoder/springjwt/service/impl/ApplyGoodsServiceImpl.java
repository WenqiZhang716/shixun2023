package com.bezkoder.springjwt.service.impl;

import com.bezkoder.springjwt.models.ApplyGoods;
import com.bezkoder.springjwt.repository.ApplyGoodsRepository;
import com.bezkoder.springjwt.service.IApplyGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplyGoodsServiceImpl implements IApplyGoodsService {

    @Autowired(required = false)
    ApplyGoodsRepository applyGoodsRepository;

    @Override
    public List<ApplyGoods> getList() {
        return applyGoodsRepository.findAll();
    }

    @Override
    public List<ApplyGoods> getByApplyId(int apply_id) {
        return applyGoodsRepository.findByApplyId(apply_id);
    }

    @Override
    public int saveApplyGoods(int apply_id, int store_id) {
        ApplyGoods applyGoods = new ApplyGoods();
        applyGoods.setApplyId(apply_id);
        applyGoods.setStoreId(store_id);
        applyGoodsRepository.save(applyGoods);

        return 0;
    }
}
