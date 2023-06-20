package org.zwq.manifest.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.zwq.manifest.entity.GoodType;

import java.util.List;

/**
 * @author zhangwq
 */
@Repository
public interface GoodTypeRepository extends JpaRepository<GoodType,Long> {
    GoodType findById(int id);
    List<GoodType>findAll();
}
