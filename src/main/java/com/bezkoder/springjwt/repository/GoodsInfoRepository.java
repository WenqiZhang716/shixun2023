package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.GoodsInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface GoodsInfoRepository extends JpaRepository<GoodsInfo,Long> {

    GoodsInfo findById(int id);


}
