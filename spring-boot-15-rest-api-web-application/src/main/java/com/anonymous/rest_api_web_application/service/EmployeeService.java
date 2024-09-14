package com.anonymous.rest_api_web_application.service;

import com.anonymous.rest_api_web_application.dto.EmployeeDto;
import com.anonymous.rest_api_web_application.model.Employee;

import java.util.List;

public interface EmployeeService {

    // add service methods in here

    EmployeeDto createEmployee(EmployeeDto employeeDto);

    EmployeeDto getEmployeeById(Long id);

    List<EmployeeDto> getAllEmployees();

    EmployeeDto updateEmployee(EmployeeDto employeeDto);

    void deleteEmployee(Long id);
}
