package com.bezkoder.springjwt.repository;
import com.bezkoder.springjwt.models.TransportStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhangwq
 */
@Repository
public interface TransportStepRepository extends JpaRepository<TransportStep, Long> {

}