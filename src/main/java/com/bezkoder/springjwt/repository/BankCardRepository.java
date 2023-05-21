package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.BankCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author zhangwq
 */
@Repository
@Transactional
public interface BankCardRepository extends JpaRepository<BankCard,Long> {
    List<BankCard> findAllByUserId(Long userId);
    @Modifying
    @Query(value="delete from BankCard b where b.userId=:userId and b.orders=:order")
    int deleteByOrdersAndUserId(@Param("order")int order,@Param("userId") Long userId);
    Optional<BankCard> findByUserIdAndOrders(Long userId,int orders);
    @Modifying
    @Query(value="update BankCard b set b.amount=:pay where b.id=:cardId")
    int updateAmount(@Param("cardId")int cardId,@Param("pay")double pay);
}


//import java.util.List;
//
//@Transactional
//public interface AreaInfoRepository extends JpaRepository<AreaInfo,Long> {
//
//    List<AreaInfo> findByAreaName(String areaName);
//    int deleteByAreaName(String areaName);
//
//    List<AreaInfo> findByType(int type);
//
//    @Modifying
//    @Query(value="update AreaInfo u set u.remainSpace=:newRemainSpace+u.remainSpace where u.id=:id")
//    int updateRemainSpace(@Param("id") int id, @Param("newRemainSpace") double newRemainSpace);
//    @Modifying
//    @Query(value="update AreaInfo u set u.remain1=:num+u.remain1,u.totalSum=u.totalSum-:num where u.id=:id")
//    int updateRemain1(@Param("id") int id, @Param("num") int num);
//    @Modifying
//    @Query(value="update AreaInfo u set u.remain2=:num+u.remain2,u.totalSum=u.totalSum-:num where u.id=:id")
//    int updateRemain2(@Param("id") int id, @Param("num") int num);
//    @Modifying
//    @Query(value="update AreaInfo u set u.remain3=:num+u.remain3,u.totalSum=u.totalSum-:num where u.id=:id")
//    int updateRemain3(@Param("id") int id, @Param("num") int num);
//    @Modifying
//    @Query(value="update AreaInfo u set u.remain4=:num+u.remain4,u.totalSum=u.totalSum-:num where u.id=:id")
//    int updateRemain4(@Param("id") int id, @Param("num") int num);
//
//}



//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Modifying;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Transactional
//public interface StoreInfoRepository extends JpaRepository<StoreInfo,Long> {
//
//    List<StoreInfo> findByGoodsId(int goodsId);
//    List<StoreInfo> findByApplyId(int applyId);
//
//    List<StoreInfo> findByAreaIdAndShelveX(int AreaId,int ShelveX);
//    int deleteByGoodsId(int goodsId);
//    int deleteById(int id);
//
//    @Query(nativeQuery=true,value="select * from Store_info where goods_id=:goodsId limit :num")
//    List<StoreInfo> limitFindByGoodsId(@Param("goodsId")int goodsId, @Param("num")int num);
//    @Modifying
//    @Query(value="update StoreInfo u set u.time=u.time+1")
//    int updateTime();
//}