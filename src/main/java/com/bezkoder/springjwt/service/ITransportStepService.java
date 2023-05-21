package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.models.TransportStep;

import java.util.List;

/**
 * @author zhangwq
 */
public interface ITransportStepService {
    List<TransportStep> getStepIdByString(String address);
}
