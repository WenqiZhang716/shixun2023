package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.AreaType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface AreaTypeRepository extends JpaRepository<AreaType,Long> {
    List<AreaType> findByName(String name);
    int deleteByName(String name);
    @Modifying
    @Query(value="update AreaType u set u.name=:newName where u.name like %:name")
    int updateName(@Param("name") String name, @Param("newName") String newName);
}
