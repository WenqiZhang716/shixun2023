package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.models.ApplyInfo;
import com.bezkoder.springjwt.models.User;

import java.util.List;

/**
 * @author zhangwq
 */
public interface IApplyInfoService {
    //int saveApplyInfo(int user_id,int type);
    int saveApplyInfo(ApplyInfo applyInfo);
    List<ApplyInfo> findApplyByUserId(int user_id);
    List<ApplyInfo> getApplyList();
    ApplyInfo findByApplyId(int id);
    int getTypeCount(int type);
   // 感觉这张表不可以删除或修改，先暂定没有
    //int deleteUserByName(String name);
    //int changePW(String name, String newPW);
}
