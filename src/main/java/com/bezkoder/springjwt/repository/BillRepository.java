package com.bezkoder.springjwt.repository;
import com.bezkoder.springjwt.models.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zhangwq
 */
@Repository
public interface BillRepository extends JpaRepository<Bill,Long> {
}
