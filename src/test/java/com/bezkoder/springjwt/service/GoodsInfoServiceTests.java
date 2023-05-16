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
public class GoodsInfoServiceTests {
    @Autowired(required = false)
    private IGoodsInfoService goodsInfoService;

//    @Test
//    public void save(){
//        String name ="asdf";
//        int num=7;
//        int owner_id=2;
//        int space=10;
//        int type=1;
//        goodsInfoService.saveGoodsInfo(name,num,owner_id,space,type);
//    }
    @Test
    public void update(){
        goodsInfoService.changeSpace(1,10);
    }
//    @Test
//    public void delete(){
//        goodsInfoService.deleteByOwnerId(2);
//    }
}
