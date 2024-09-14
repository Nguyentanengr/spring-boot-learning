package com.nguyentan.demoApplication.repositories;

import com.nguyentan.demoApplication.enitities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByAtk(int atk);

    List<User> findAllByAtk(int atk);

    List<User> findByHpAndStamina(int hp, int stamina);

    // native query
    @Query(value = "SELECT DISTINCT * FROM User u WHERE u.hp = :hp OR u.stamina = :stamina", nativeQuery = true)
    List<User> findUserDistinctByHpOrStamina(@Param("hp") int hp, @Param("stamina") int stamina);

    // JPQL query
    @Query("SELECT u FROM User u ORDER BY u.hp DESC")
    List<User> findByHpDesc();
}
