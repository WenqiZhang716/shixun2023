package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.GoodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhangwq
 */
@Repository
public interface GoodTypeRepository extends JpaRepository<GoodType,Long> {
}
