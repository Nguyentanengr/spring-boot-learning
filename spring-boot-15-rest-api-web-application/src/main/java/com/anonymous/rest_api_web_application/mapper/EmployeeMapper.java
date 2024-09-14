package com.anonymous.rest_api_web_application.mapper;

import com.anonymous.rest_api_web_application.dto.EmployeeDto;
import com.anonymous.rest_api_web_application.model.Employee;
import com.anonymous.rest_api_web_application.repository.EmployeeRepository;

public class EmployeeMapper {

    public static EmployeeDto mapToEmployeeDto(Employee employee) {
        return new EmployeeDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail()
        );
    }

    public static Employee mapToEmployee(EmployeeDto employeeDto) {
        return new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getEmail()
        );
    }
}
