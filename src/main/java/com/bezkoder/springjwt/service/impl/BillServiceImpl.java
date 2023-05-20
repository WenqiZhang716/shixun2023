package com.bezkoder.springjwt.service.impl;

import com.bezkoder.springjwt.service.IBillService;
import org.springframework.stereotype.Service;

/**
 * @author zhangwq
 */
@Service
public class BillServiceImpl implements IBillService {
    @Override
    public int createOne(int manifestId, double payment, double payoff, int payWay, String payName) {
        return 0;
    }
}