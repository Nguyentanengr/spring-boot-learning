package com.anonymous.shop_application.repositories;

import com.anonymous.shop_application.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("""
            SELECT u FROM User u WHERE (:keyword = '' OR u.fullName LIKE %:keyword% OR 
            u.phoneNumber LIKE %:keyword% OR u.address LIKE %:keyword%)
    """)
    Page<User> findAllByFilter( @Param("keyword") String keyword, Pageable pageable);

    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByFullName(String fullName);

    Optional<User> findByPhoneNumber(String phoneNumber);
}
