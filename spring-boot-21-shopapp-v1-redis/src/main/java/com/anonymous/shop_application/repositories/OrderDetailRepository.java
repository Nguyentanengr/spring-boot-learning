package com.anonymous.shop_application.repositories;

import com.anonymous.shop_application.models.OrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    @Query("""
            SELECT o FROM OrderDetail o WHERE 
                (:orderId IS NULL OR :orderId = 0 OR o.order.id = :orderId) AND 
                (:amount IS NULL OR :amount = 0 OR o.price >= :amount OR o.numberOfProducts >= :amount)
            """)
    Page<OrderDetail> findAllOrderDetailByFilter(
            @Param("amount") Float amount,
            @Param("orderId") Long orderId,
            Pageable pageable
    );
}
