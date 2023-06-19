package org.ejavaexample.transport.repository;
import org.ejavaexample.transport.entity.Transport;
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
       @Query(value="update Transport t set t.arriveTime=:arriveTime,t.status=:status,t.transporterId=:transporterId where t.id=:id and t.isValid=1")
       int updateArriveTimeAndStatusAndTransporterIdById(@Param("id")int id,@Param("arriveTime") Date arriveTime,
                                                         @Param("status")int status,@Param("transporterId")int transporterId);

       @Modifying
       @Query(value="update Transport t set t.leaveTime=:leaveTime,t.status=:status where t.id=:id and t.isValid=1")
       int updateLeaveTimeAndStatus(@Param("id")int id,@Param("leaveTime") Date liveTime,
                                                         @Param("status")int status);


       @Modifying
       @Query(value="update Transport t set t.status=:status where t.id=:id")
       int updateStatusById(@Param("id")int id,@Param("status")int status);

       Optional<Transport> findFirstByStatus(int status);

       @Modifying
       @Query(value="update Transport t set t.isValid=1 where t.manifestId=:manifestId")
       int updateValidByManifestId(int manifestId);

       @Modifying
       @Query(value="select a from Transport a where a.status=2 and a.orders>1 and a.stepId=0")
       List<Transport>findAllFinish();

       @Modifying
       @Query(value="select a from Transport a where a.status>0 and a.orders=1 and a.stepId=0")
       List<Transport>findAllChange();

       Optional<Transport>findFirstByStatusAndIsValid(int status,int isValid);
}
