package org.ejavaexample.manifest.repository;
import org.ejavaexample.manifest.entity.GoodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhangwq
 */
@Repository
public interface GoodTypeRepository extends JpaRepository<GoodType,Long> {
    GoodType findById(int id);
    List<GoodType>findAll();
}
