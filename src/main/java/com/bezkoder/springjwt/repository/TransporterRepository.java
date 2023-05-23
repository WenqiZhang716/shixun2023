package com.bezkoder.springjwt.repository;
import com.bezkoder.springjwt.models.Transporter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author zhangwq
 */
@Repository
public interface TransporterRepository extends JpaRepository<Transporter, Long> {
   Optional<Transporter> findByUserId(Long userId);
}
