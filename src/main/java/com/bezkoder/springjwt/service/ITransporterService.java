package com.bezkoder.springjwt.service;

import com.bezkoder.springjwt.models.Manifest;

import java.util.List;

public interface ITransporterService {
    List<Manifest>getListByStatus(Long userId,int status);
    int changeManiStatus(int manifestId,int transporterId);
    int getIdByUserId(Long userId);
    int beginTimetask(int a);
}
