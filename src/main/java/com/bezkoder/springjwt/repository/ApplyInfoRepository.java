package com.bezkoder.springjwt.repository;

import com.bezkoder.springjwt.models.ApplyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author zhangwq
 */
@Transactional
public interface ApplyInfoRepository extends JpaRepository<ApplyInfo,Long> {
    List<ApplyInfo>findByUserId(int user_id);
    ApplyInfo findByGoodsId(int goods_id);
    Optional<ApplyInfo> findById(int id);
    List<ApplyInfo>findByType(int type);

}
