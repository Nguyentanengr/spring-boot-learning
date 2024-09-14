package com.anonymous.rest_api_web_application.repository;

import com.anonymous.rest_api_web_application.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}

