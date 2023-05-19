package com.bezkoder.springjwt.repository;
import com.bezkoder.springjwt.models.Transporter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhangwq
 */
@Repository
public interface TransporterRepository extends JpaRepository<Transporter, Long> {
}
