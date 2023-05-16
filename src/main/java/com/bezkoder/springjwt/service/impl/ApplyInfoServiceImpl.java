package com.bezkoder.springjwt.service.impl;

import com.bezkoder.springjwt.models.ApplyInfo;
import com.bezkoder.springjwt.repository.ApplyInfoRepository;
import com.bezkoder.springjwt.service.IApplyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;


/**
 * @author zhangwq
 */
@Service
public class ApplyInfoServiceImpl implements IApplyInfoService {
    @Autowired
    private ApplyInfoRepository applyInfoRepository;

    @Override
    public int saveApplyInfo(ApplyInfo applyInfo) {

        applyInfoRepository.saveAndFlush(applyInfo);
        return 1;
    }

    public ApplyInfoRepository getApplyInfoRepository() {
        return applyInfoRepository;
    }

    public void setApplyInfoRepository(ApplyInfoRepository applyInfoRepository) {
        this.applyInfoRepository = applyInfoRepository;
    }

    @Override
    public List<ApplyInfo> findApplyByUserId(int user_id) {
        List<ApplyInfo> list=applyInfoRepository.findByUserId(user_id);
        return list;
    }

    @Override
    public List<ApplyInfo> getApplyList() {
        List<ApplyInfo> list=applyInfoRepository.findAll();
        return list;
    }

    @Override
    public ApplyInfo findByApplyId(int id) {
        Optional<ApplyInfo> info=applyInfoRepository.findById(id);
        if(info.isPresent()){
            return info.get();
        }
        return null;
    }

    @Override
    public int getTypeCount(int type) {
        int count=applyInfoRepository.findByType(type).size();
        return count;
    }
}
