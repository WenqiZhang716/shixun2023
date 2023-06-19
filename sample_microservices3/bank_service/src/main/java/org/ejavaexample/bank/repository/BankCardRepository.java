package org.ejavaexample.bank.repository;

import org.ejavaexample.bank.entity.BankCard;
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
public interface BankCardRepository extends JpaRepository<BankCard,Long> {
    List<BankCard> findAllByUserId(Long userId);
    @Modifying
    @Query(value="delete from BankCard b where b.userId=:userId and b.orders=:order")
    int deleteByOrdersAndUserId(@Param("order")int order,@Param("userId") Long userId);
    @Modifying
    @Query(value="update BankCard b set b.orders=b.orders-1 where b.orders>:order and b.userId=:userId")
    int updateOrderByOrder(@Param("userId") Long userId, @Param("order") int order);
    Optional<BankCard> findByUserIdAndOrders(Long userId, int orders);
    @Modifying
    @Query(value="update BankCard b set b.amount=:pay where b.id=:cardId")
    int updateAmount(@Param("cardId")int cardId,@Param("pay")double pay);
}
