package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.GoodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author zhangwq
 */
@Repository
public interface GoodTypeRepository extends JpaRepository<GoodType,Long> {
    GoodType findById(int id);
    List<GoodType>findAll();
}
