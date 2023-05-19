package com.bezkoder.springjwt.repository;
import com.bezkoder.springjwt.models.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhangwq
 */
@Repository
public interface TransportRepository extends JpaRepository<Transport, Long> {

}
