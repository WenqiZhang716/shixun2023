package com.bezkoder.springjwt.service;

public interface IBillService {

    int createOne(int manifestId,double payment,double payoff,int payWay,String payName);
}
