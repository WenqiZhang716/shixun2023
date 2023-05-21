package com.bezkoder.springjwt.repository;
import com.bezkoder.springjwt.models.TransportStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhangwq
 */
@Repository
@Transactional
public interface TransportStepRepository extends JpaRepository<TransportStep, Long> {

    @Modifying
    @Query(value="select t.name,t.type from TransportStep t where t.provinces like %:province")
    List<TransportStep> findListByProvinces(@Param("province")String provinces);

    List<TransportStep>findAllByProvincesLike(String provinces);

}