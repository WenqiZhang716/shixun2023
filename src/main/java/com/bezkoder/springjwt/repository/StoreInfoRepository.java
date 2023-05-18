//package com.bezkoder.springjwt.repository;
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
