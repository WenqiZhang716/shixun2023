package com.bezkoder.springjwt.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//标注测试类
@SpringBootTest
//表示启动测试类，不是最外层，不写可能报错
@RunWith(SpringRunner.class)
public class ApplyGoodsServiceTests {
    @Autowired(required = false)
    private IApplyGoodsService applyGoodsService;
    @Test
    public void saveTest(){
        int apply_id=1;
        int goods_id=2;
        applyGoodsService.saveApplyGoods(apply_id,goods_id);
    }

    @Test
    public void GetByApplyId(){
        int apply_id=1;
        applyGoodsService.getByApplyId(apply_id);
    }

    @Test
    public void GetList(){
        applyGoodsService.getList();
    }
}
