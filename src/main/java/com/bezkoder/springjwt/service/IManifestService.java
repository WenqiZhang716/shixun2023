package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.models.Manifest;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author zhangwq
 */
public interface IManifestService {
    int createManifest(Long userId,int goodsType,double weight,int transportType,
                       String beginAddress,String endAddress,int payType,
                       String receiverName,String receiverPhone,String beizhu);
    int cancelOne(int manifestId);
    int changeAddress(int manifestId,String newAddress);
    List<Object> findAllManifestByStatus(int status,Long userId);

}

