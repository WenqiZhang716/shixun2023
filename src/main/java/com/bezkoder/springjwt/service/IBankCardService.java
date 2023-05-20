package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.models.BankCard;

import java.util.List;

/**
 * @author zhangwq
 */
public interface IBankCardService {
    int addOne(Long userId,String bankCardNum,String bankName);
    int deleteOne(Long userId, int order);
    List<BankCard> getList(Long userId);
}
