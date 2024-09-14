package com.anonymous.simple_web_application.service;

import com.anonymous.simple_web_application.model.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();

    void saveEmployee(Employee employee);

    Employee getEmployeeById(Long id);

    void deleteEmployee(Employee employee);

    Page<Employee> findPaginated(int pageNo, int pageSize);

}
