package com.anonymous.shop_application.repositories;

import com.anonymous.shop_application.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
             SELECT o FROM Order o WHERE 
                (:userId IS NULL OR :userId = 0 OR o.user.id = :userId) AND 
                (:amount IS NULL OR :amount = 0 OR o.totalMoney >= :amount)
    """)
    Page<Order> findAllOrderByFilter(
            @Param("amount") Float amount,
            @Param("userId") Long userId,
            Pageable pageable
    );

    @Query("""
            SELECT o FROM Order o WHERE o.user.id = :userId
    """)
    Page<Order> findOrderByUserId(
            @Param("userId") Long userId,
            Pageable pageable
    );
}
