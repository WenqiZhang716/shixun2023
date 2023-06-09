package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.models.Bill;
import com.bezkoder.springjwt.models.Manifest;

import java.util.List;
import java.util.Optional;

public interface IBillService {

    int createOne(Long userId,String username,int manifestId);
    int payOne(Long userId,int billId,int cardOrder,String password);
    int cancelBill(int bill);
    List<Object>getBillByType(Long userId,int type);
    Bill getOneBill(int manifestId,int userId);
}
