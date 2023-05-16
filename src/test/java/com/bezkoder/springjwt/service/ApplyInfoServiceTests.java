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
public class ApplyInfoServiceTests {

    @Autowired(required = false)
    private IApplyInfoService applyInfoService;

//    @Test
//    public void saveApply(){
//       int user_id=1;
//       int type=0;
//       applyInfoService.saveApplyInfo(user_id,type);
//    }

    @Test
    public void findApplyByUserId(){
        int user_id=1;
        applyInfoService.findApplyByUserId(user_id);
    }

    @Test
    public void getApplyList(){
        applyInfoService.getApplyList();
    }




}
