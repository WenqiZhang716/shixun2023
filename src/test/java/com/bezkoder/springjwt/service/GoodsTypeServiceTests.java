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
public class GoodsTypeServiceTests {
    @Autowired(required = false)
    IGoodsTypeService goodsTypeService;

    @Test
    public void save(){
       goodsTypeService.saveGoodsType("adwac");
    }
}
