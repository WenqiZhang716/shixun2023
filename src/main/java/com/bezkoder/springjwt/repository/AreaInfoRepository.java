package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.AreaInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface AreaInfoRepository extends JpaRepository<AreaInfo,Long> {

    List<AreaInfo> findByAreaName(String areaName);
    int deleteByAreaName(String areaName);

    List<AreaInfo> findByType(int type);

    @Modifying
    @Query(value="update AreaInfo u set u.remainSpace=:newRemainSpace+u.remainSpace where u.id=:id")
    int updateRemainSpace(@Param("id") int id, @Param("newRemainSpace") double newRemainSpace);
    @Modifying
    @Query(value="update AreaInfo u set u.remain1=:num+u.remain1,u.totalSum=u.totalSum-:num where u.id=:id")
    int updateRemain1(@Param("id") int id, @Param("num") int num);
    @Modifying
    @Query(value="update AreaInfo u set u.remain2=:num+u.remain2,u.totalSum=u.totalSum-:num where u.id=:id")
    int updateRemain2(@Param("id") int id, @Param("num") int num);
    @Modifying
    @Query(value="update AreaInfo u set u.remain3=:num+u.remain3,u.totalSum=u.totalSum-:num where u.id=:id")
    int updateRemain3(@Param("id") int id, @Param("num") int num);
    @Modifying
    @Query(value="update AreaInfo u set u.remain4=:num+u.remain4,u.totalSum=u.totalSum-:num where u.id=:id")
    int updateRemain4(@Param("id") int id, @Param("num") int num);

}
