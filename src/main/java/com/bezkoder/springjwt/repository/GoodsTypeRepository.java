package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.GoodsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface GoodsTypeRepository extends JpaRepository<GoodsType,Long> {
    GoodsType findById(int id);
}
