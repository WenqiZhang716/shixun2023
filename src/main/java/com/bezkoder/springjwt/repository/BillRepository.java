package com.bezkoder.springjwt.repository;
import com.bezkoder.springjwt.models.Bill;
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
public interface BillRepository extends JpaRepository<Bill,Long> {

    Optional<Bill>findById(int id);
    @Modifying
    @Query(value="update Bill b set b.status=:status,b.bankCardId=:bankCardId where b.id=:billId")
    int updatePayStatus(@Param("billId")int billId,@Param("bankCardId")int bankCardId,@Param("status")int status);
    List<Bill>findAllByUserIdAndStatus(Long userId,int status);
    List<Bill>findAllByUserId(Long userId);
    List<Bill>findAllByUserIdAndPayWay(Long userId,int payWay);
    @Modifying
    @Query(value="update Bill b set b.status=:status where b.manifestId=:manifestId")
    int updateStatusByManifestId(@Param("manifestId")int manifestId,@Param("status")int status);

    Optional<Bill>findByManifestId(int manifestId);
}
