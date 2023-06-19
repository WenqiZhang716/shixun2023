package org.ejavaexample.manifest.repository;
import org.ejavaexample.manifest.entity.Manifest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


/**
 * @author zhangwq
 */
@Repository
@Transactional
public interface ManifestRepository extends JpaRepository<Manifest,Long> {
    Optional<Manifest> findById(int id);
//    int save(Manifest manifest);
    @Modifying
    @Query(value="update Manifest m set m.status=3 where m.id=:id")
    int cancelIt(@Param("id")int id);

    @Modifying
    @Query(value="update Manifest m set m.endAddress=:newAddress where m.id=:id")
    int changeAddress(@Param("id")int id,@Param("newAddress")String newAddress);
    List<Manifest> findAllByStatusAndUserId(int status,Long userId);
    List<Manifest> findAllByUserId(Long userId);
    @Modifying
    @Query(value="update Manifest m set m.isPay=1 where m.id=:manifestId")
    int updateIsPay(@Param("manifestId")int manifestId);
    @Modifying
    @Query(value="update Manifest m set m.beginId=:beginId, m.endId=:endId where m.id=:manifestId")
    int updateBeginAndEndId(@Param("manifestId")int manifestId,@Param("beginId")int beginId,@Param("endId")int endId);

    @Modifying
    @Query(value="update Manifest m set m.status=:status where m.id=:manifestId")
    int updateStatusByManiId(@Param("manifestId")int manifestId,@Param("status")int status);

    List<Manifest>findAllByStatusAndIsPayAndPayTypeAndUserId(int status,int isPay,int payType,Long userId);
    List<Manifest>findAllByIsPayAndPayTypeAndUserId(int isPay,int payType,Long userId);
    List<Manifest>findAllByPayTypeAndUserId(int payType,Long userId);

}
