package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.models.Transport;
import com.bezkoder.springjwt.models.TransportStep;

import java.util.HashMap;
import java.util.List;

/**
 * @author zhangwq
 */
public interface ITransportService {
    List<Transport> pathPlan(int manifestId);
    int CreateOne(int manifestId,String step,int stepId,int transporterId,int type,int orders);
    int deleteAll(int manifestId);
    List<Transport> getPathList(int manifestId);
}
