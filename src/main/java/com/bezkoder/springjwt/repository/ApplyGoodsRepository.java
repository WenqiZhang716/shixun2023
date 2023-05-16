package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.ApplyGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ApplyGoodsRepository extends JpaRepository<ApplyGoods,Long> {
    List<ApplyGoods> findByApplyId(int applyId);
}
