package com.bezkoder.springjwt.repository;
import com.bezkoder.springjwt.models.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
       List<Transport>findAllByStepId(int stepId);
       List<Transport> findAllByManifestIdAndType(int manifestId,int type);
       @Modifying
       @Query(value="update Transport t set t.arriveTime=:arriveTime,t.status=:status,t.transporterId=:transporterId where t.id=:id")
       int updateArriveTimeAndStatusAndTransporterIdById(@Param("id")int id,@Param("arriveTime") Date arriveTime,
                                                         @Param("status")int status,@Param("transporterId")int transporterId);

       @Modifying
       @Query(value="update Transport t set t.status=:status where t.id=:id")
       int updateStatusById(@Param("id")int id,@Param("status")int status);
}
