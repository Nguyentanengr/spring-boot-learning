package com.anonymous.springboot.service;

import com.anonymous.springboot.exception.ResourceNotFoundException;
import com.anonymous.springboot.model.Employee;
import com.anonymous.springboot.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        employeeRepository.findByEmail(employee.getEmail())
                .ifPresent(employee1 -> {throw new ResourceNotFoundException("Employee already exists with given email");});
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exists with given id"));
    }

    @Override
    public Employee updateEmployee(Long id, Employee employee) {
        Employee theEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not exists with given one"));

        if (!employee.getEmail().equals(theEmployee.getEmail())) {
            employeeRepository.findByEmail(employee.getEmail())
                    .ifPresent(em -> {
                        throw new ResourceNotFoundException("Employee already exists with given email");});
        }

        theEmployee.setEmail(employee.getEmail());
        theEmployee.setFirstName(employee.getFirstName());
        theEmployee.setLastName(employee.getLastName());

        return employeeRepository.save(theEmployee);
    }
}
