package com.anonymous.springboot.repository;

import com.anonymous.springboot.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.lang.annotation.Native;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    // define custom query using JPQL with index params
    @Query("select e from Employee e where e.firstName = ?1 and e.lastName = ?2")
    Employee findByJPQLIndexParam(String firstName, String lastName);

    // define custom query using JPQL with named params
    @Query("select e from Employee e where e.firstName = :firstName and e.lastName = :lastName")
    Employee findByJPQLNamedParam(@Param("firstName") String firstName, @Param("lastName") String lastName);

    // define custom query using Native SQL with index param
    @Query(value = "select * from employees e where e.first_name = ?1 and e.last_name = ?2", nativeQuery = true)
    Employee findByNativeSQLIndexParam(String firstName, String lastName);

    // define custom query using Native SQL with named param
    @Query(value = "select * from employees e where e.first_name = :firstName and e.last_name = :lastName", nativeQuery = true)
    Employee findByNativeSQLNamedParam(@Param("firstName") String firstName, @Param("lastName") String lastName);

}
