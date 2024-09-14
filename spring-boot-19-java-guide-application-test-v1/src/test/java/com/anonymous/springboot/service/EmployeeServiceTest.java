package com.anonymous.springboot.service;

import com.anonymous.springboot.controller.EmployeeController;
import com.anonymous.springboot.exception.ResourceNotFoundException;
import com.anonymous.springboot.model.Employee;
import com.anonymous.springboot.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Test for employee service")
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Autowired
    private EmployeeController controller;

    private Long employeeIdRequest;
    private Employee employeeCreationRequest;
    private Employee employeeUpdateRequest;

    private Employee mockEmployeeResponse;
    private Employee mockEmployeeCreationResponse;
    private Employee mockEmployeeUpdateResponse;
    private List<Employee> mockEmployeesResponse;

    @BeforeEach
    public void initialize() {
        // request
        employeeIdRequest = 1L;
        employeeCreationRequest = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("doe@gmail.com")
                .build();
        employeeUpdateRequest = Employee.builder()
                .id(1L)
                .firstName("John")
                .lastName("Cena")
                .email("cena@gmail.com")
                .build();

        // response
        mockEmployeeResponse = Employee.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("doe@gmail.com")
                .build();
        mockEmployeeCreationResponse = Employee.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("doe@gmail.com")
                .build();
        mockEmployeeUpdateResponse = Employee.builder()
                .id(1L)
                .firstName("John")
                .lastName("Cena")
                .email("cena@gmail.com")
                .build();
        mockEmployeesResponse = List.of(mockEmployeeCreationResponse);

    }

    @Test
    @DisplayName("Test the valid operation of get employee by id")
    public void givenValidEmployeeId_whenGetById_thenEmployeeIsFound() {

        // given - precondition or setup
        given(employeeRepository.findById(employeeIdRequest))
                .willReturn(Optional.of(mockEmployeeResponse));

        // when - action or the behaviour that we are going test
        Employee employeeResponse = employeeService.getEmployeeById(employeeIdRequest);

        // then - verify the output
        assertThat(employeeResponse).isNotNull();
        assertThat(employeeResponse.getId()).isEqualTo(mockEmployeeResponse.getId());
    }

    @Test
    @DisplayName("Test the invalid operation of get employee by id")
    public void givenInvalidEmployeeId_whenGetById_thenEmployeeIsNotFound() {

        // given - precondition or setup
        employeeIdRequest = -1L;
        given(employeeRepository.findById(employeeIdRequest))
                .willReturn(Optional.empty());

        // when - action or the behaviour that we are going test
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> employeeService.getEmployeeById(employeeIdRequest));

        // then - verify the output
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Employee not exists with given id");
    }

    @Test
    @DisplayName("Test the valid operation of get all employee")
    public void givenNoThing_whenGetAll_thenEmployeeListIsFound() {

        // given - precondition or setup
        given(employeeRepository.findAll()).willReturn(mockEmployeesResponse);

        // when - action or the behaviour that we are going test
        List<Employee> employeesResponse = employeeService.getAllEmployees();

        // then - verify the output
        assertThat(employeesResponse).isNotNull();
        assertThat(employeesResponse.size()).isEqualTo(mockEmployeesResponse.size());
    }

    @Test
    @DisplayName("Test the valid operation of save employee")
    public void givenValidEmployee_whenSave_thenEmployeeIsSaved() {

        // given - precondition or setup

        given(employeeRepository.findByEmail(employeeCreationRequest.getEmail()))
                .willReturn(Optional.empty());
        given(employeeRepository.save(employeeCreationRequest))
                .willReturn(mockEmployeeCreationResponse);

        // when - action or the behaviour that we are going test
        Employee employeeResponse = employeeService.saveEmployee(employeeCreationRequest);

        // then - verify the output
        assertThat(employeeResponse).isNotNull();
        assertThat(employeeResponse.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Test the invalid operation of save employee")
    public void givenInvalidEmployeeEmail_whenSave_thenEmployeeIsNotSaved() {

        // given - precondition or setup
        given(employeeRepository.findByEmail(employeeCreationRequest.getEmail()))
                .willReturn(Optional.of(mockEmployeeCreationResponse));

        // when - action or the behaviour that we are going test
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> employeeService.saveEmployee(employeeCreationRequest));

        // then - verify the output
        verify(employeeRepository, never()).save(any(Employee.class));
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Employee already exists with given email");
    }

    @Test
    @DisplayName("Test the valid operation of update employee")
    public void givenValidEmployee_whenUpdate_thenEmployeeIsUpdated() {

        // given - precondition or setup
        given(employeeRepository.findById(employeeIdRequest))
                .willReturn(Optional.of(mockEmployeeResponse));
        given(employeeRepository.findByEmail(employeeUpdateRequest.getEmail()))
                .willReturn(Optional.empty());
        given(employeeRepository.save(any()))
                .willReturn(mockEmployeeUpdateResponse);

        // when - action or the behaviour that we are going test
        Employee employeeResponse = employeeService.updateEmployee(employeeIdRequest, employeeUpdateRequest);

        // then - verify the output
        assertThat(employeeResponse).isNotNull();
        assertThat(employeeResponse.getEmail()).isEqualTo(mockEmployeeResponse.getEmail());
        assertThat(employeeResponse.getFirstName()).isEqualTo(mockEmployeeResponse.getFirstName());
        assertThat(employeeResponse.getLastName()).isEqualTo(mockEmployeeResponse.getLastName());
    }

    @Test
    @DisplayName("Test the invalid operation of update employee")
    public void

    givenInvalidEmployeeId_whenUpdate_thenEmployeeIsNotUpdated() {

        // given - precondition or setup
        given(employeeRepository.findById(employeeIdRequest))
                .willReturn(Optional.empty());

        // when - action or the behaviour that we are going test
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> employeeService.updateEmployee(employeeIdRequest, employeeUpdateRequest));

        // then - verify the output
        verify(employeeRepository, never()).findByEmail(anyString());
        verify(employeeRepository, never()).save(any(Employee.class));
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Employee not exists with given one");

    }

    @Test
    @DisplayName("Test the invalid operation of update employee")
    public void givenInvalidEmployeeUsername_whenUpdate_thenEmployeeIsNotUpdated() {

        // given - precondition or setup
        given(employeeRepository.findById(employeeIdRequest))
                .willReturn(Optional.of(mockEmployeeResponse));
        given(employeeRepository.findByEmail(employeeUpdateRequest.getEmail()))
                .willReturn(Optional.of(mockEmployeeResponse));

        // when - action or the behaviour that we are going test
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> employeeService.updateEmployee(employeeIdRequest, employeeUpdateRequest));

        // then - verify the output
        verify(employeeRepository, never()).save(any(Employee.class));
        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Employee already exists with given email");
    }

}
