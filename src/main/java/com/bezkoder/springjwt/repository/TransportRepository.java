package com.bezkoder.springjwt.repository;
import com.bezkoder.springjwt.models.Transport;
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
public interface TransportRepository extends JpaRepository<Transport, Long> {
       List<Transport>findAllByManifestId(int manifestId);
       @Modifying
       @Query(value="delete from Transport t where t.manifestId=:id")
       int deleteAllByManifestId(@Param("id")int manifestId);
}
