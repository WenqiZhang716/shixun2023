package com.bezkoder.springjwt.service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zhangwq
 */
public interface IManifestService {
    int createManifest(Long userId,int goodsType,double weight,int transportType,
                       String beginAddress,String endAddress,int payType,
                       String receiverName,String receiverPhone);
    int cancelOne(int manifestId);
}

