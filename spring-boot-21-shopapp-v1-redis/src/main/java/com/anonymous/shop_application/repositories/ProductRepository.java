package com.anonymous.shop_application.repositories;

import com.anonymous.shop_application.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("""
            SELECT p FROM Product p WHERE 
                (:categoryId IS NULL OR :categoryId = 0 OR p.category.id = :categoryId) AND 
                (:keyword IS NULL OR :keyword = '' OR p.name LIKE %:keyword% OR p.description LIKE %:keyword%)
            """)
    Page<Product> findAllProductByFilter(
            @Param("keyword") String keyword,
            @Param("categoryId") Long categoryId,
            Pageable pageable
    );


    boolean existsByName(String productName);
}
