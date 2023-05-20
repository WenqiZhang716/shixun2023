package com.bezkoder.springjwt.service.impl;

import com.bezkoder.springjwt.models.Manifest;
import com.bezkoder.springjwt.repository.ManifestRepository;
import com.bezkoder.springjwt.repository.UserRepository;
import com.bezkoder.springjwt.service.IManifestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author zhangwq
 */
@Service
public class ManifestServiceImpl implements IManifestService {

    @Autowired
    private ManifestRepository manifestRepository;


    @Override
    public int createManifest(Long userId, int goodsType, double weight, int transportType,
                              String beginAddress, String endAddress, int payType, String receiverName, String receiverPhone) {
        try{
            Manifest mani=new Manifest();
            mani.setUserId(userId);
            mani.setGoodTypeId(goodsType);
            mani.setBeginAddress(beginAddress);
            mani.setEndAddress(endAddress);
            mani.setWeight(weight);
            mani.setTransportType(transportType);
            mani.setPayType(payType);
            mani.setReceiverName(receiverName);
            mani.setReceiverPhone(receiverPhone);
            mani.setAmount(weight*10);
            manifestRepository.save(mani);
            return 1;
        }catch(Exception e){
            return 0;
        }
    }

    @Override
    public int cancelOne(int manifestId) {
        Optional<Manifest>mani=manifestRepository.findById(manifestId);
        if (mani.isPresent()){
            Manifest manifest=mani.get();
            if(manifest.getStatus()==0){
                manifestRepository.cancelIt(manifestId);
               return 1;
            }else{
                return 0;
            }
        }else{
            return 2;
        }

    }

    @Override
    public int changeAddress(int manifestId, String newAddress) {
        Optional<Manifest>mani=manifestRepository.findById(manifestId);
        if (mani.isPresent()){
            Manifest manifest=mani.get();
            if(manifest.getStatus()==0){
                manifestRepository.changeAddress(manifestId, newAddress);
                return 1;
            }else{
                return 0;
            }
        }else{
            return 2;
        }
    }

    @Override
    public List<Manifest> findAllManifestByStatus(int status,Long userId) {
        //0未发货、1进行中、2已完成、3已取消
        ArrayList<Manifest>list=new ArrayList<>();
        if(status==0||status==1||status==2||status==3){
            list= (ArrayList<Manifest>) manifestRepository.findAllByStatusAndUserId(status,userId);

        }else{
            list= (ArrayList<Manifest>) manifestRepository.findAllByUserId(userId);
        }
//        while(!list.isEmpty()){
//
//        }

        return list;
    }

}
