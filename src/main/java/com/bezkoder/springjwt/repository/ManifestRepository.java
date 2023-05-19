package com.bezkoder.springjwt.repository;
import com.bezkoder.springjwt.models.Manifest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


/**
 * @author zhangwq
 */
@Repository
@Transactional
public interface ManifestRepository extends JpaRepository<Manifest,Long> {
    Optional<Manifest> findById(int id);

    @Modifying
    @Query(value="update Manifest m set m.status=3 where m.id=:id")
    int cancelIt(@Param("id")int id);

}
