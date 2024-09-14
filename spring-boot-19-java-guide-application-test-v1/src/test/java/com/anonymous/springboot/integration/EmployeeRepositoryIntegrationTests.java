package com.anonymous.springboot.integration;

import com.anonymous.springboot.model.Employee;
import com.anonymous.springboot.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // mac dinh cua data jpa test la h2 database
@Transactional
@DisplayName("Test for employee repository")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // disable h2 db and able other db
public class EmployeeRepositoryIntegrationTests extends AbstractionBaseTest{

    @Autowired
    private EmployeeRepository employeeRepository;

    private Long employeeIdRequest;
    private String employeeEmailRequest;
    private Employee employeeCreationRequest;
    private Employee employeeUpdateRequest;

    @BeforeEach
    public void initialize() {

        employeeIdRequest = 1L;
        employeeEmailRequest = "doe@gmail.com";
        employeeCreationRequest = Employee.builder()
                .firstName("John")
                .lastName("Doe")
                .email("doe@gmail.com")
                .build();
        employeeUpdateRequest = Employee.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("doe@gmail.com")
                .build();
    }


    @Test
    @DisplayName("Test the valid operation of get employee by id")
    public void givenValidEmployeeId_whenFindById_thenEmployeeIsFound() {

        // given - precondition or setup
        employeeRepository.save(employeeCreationRequest);

        // when - action or the behaviour that we are going test
        Employee employeeResponse = employeeRepository.findById(employeeCreationRequest.getId()).get();

        // then - verify the output
        assertThat(employeeResponse).isNotNull();
        assertThat(employeeResponse.getId()).isEqualTo(employeeCreationRequest.getId());
    }

    @Test
    @DisplayName("Test the invalid operation of get employee by id")
    public void givenInvalidEmployeeId_whenFindById_thenEmployeeIsNotFound() {

        // given - precondition or setup
        employeeRepository.save(employeeCreationRequest);
        employeeIdRequest = -1L;

        // when - action or the behaviour that we are going test
        Employee employeeResponse = employeeRepository.findById(employeeIdRequest).orElseGet(() -> null);

        // then - verify the output
        assertThat(employeeResponse).isNull();
    }



    @Test
    @DisplayName("Test the valid operation of find by email")
    public void givenValidEmployeeEmail_whenFindByEmail_thenEmployeeIsFound() {

        // given - precondition or setup
        employeeRepository.save(employeeCreationRequest);

        // when - action or the behaviour that we are going test
        Employee employeeResponse = employeeRepository.findByEmail(employeeEmailRequest).get();

        // then - verify the output

        assertThat(employeeResponse).isNotNull();
        assertThat(employeeResponse.getId()).isEqualTo(employeeCreationRequest.getId());
    }

    @Test
    @DisplayName("Test the invalid operation of find by email")
    public void givenInvalidEmployeeEmail_whenFindByEmail_thenEmployeeIsNotFound() {

        // given - precondition or setup
        employeeRepository.save(employeeCreationRequest);
        employeeEmailRequest = "cena@gmail.com";

        // when - action or the behaviour that we are going test
        Employee employeeResponse = employeeRepository.findByEmail(employeeEmailRequest).orElseGet(() -> null);

        // then - verify the output

        assertThat(employeeResponse).isNull();
    }

    @Test
    @DisplayName("Test the valid operation of get all employee")
    public void givenNoThing_whenFindAll_thenEmployeeListIsFound() {

        // given - precondition or setup
        employeeRepository.save(employeeCreationRequest);

        // when - action or the behaviour that we are going test
        List<Employee> employeeResponse = employeeRepository.findAll();

        // then - verify the output
        assertThat(employeeResponse).isNotNull();  // return empty list
        assertThat(employeeResponse.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Test the valid operation of save employee")
    public void givenValidEmployee_whenSave_thenEmployeeIsSaved() {

        // given - precondition or setup

        // when - action or the behaviour that wa are going test
        Employee employeeResponse = employeeRepository.save(employeeCreationRequest);

        // then - verify the output
        assertThat(employeeResponse).isNotNull();
        assertThat(employeeResponse.getId()).isGreaterThan(0);
    }


    @Test
    @DisplayName("Test the valid operation of update employee")
    public void givenValidEmployee_whenUpdateEmployee_thenEmployeeIsUpdated() {

        // given - precondition or setup
        employeeRepository.save(employeeCreationRequest);

        // when - action or the behaviour that we are going test
        Employee theEmployee = employeeRepository.findById(employeeCreationRequest.getId()).get();
        theEmployee.setLastName(employeeUpdateRequest.getLastName());
        theEmployee.setEmail(employeeUpdateRequest.getEmail());

        Employee employeeResponse = employeeRepository.save(theEmployee);

        // then - verify the output
        assertThat(employeeResponse).isNotNull();
        assertThat(employeeResponse.getId()).isEqualTo(theEmployee.getId());
        assertThat(employeeResponse.getLastName()).isEqualTo(theEmployee.getLastName());
        assertThat(employeeResponse.getEmail()).isEqualTo(theEmployee.getEmail());
    }

    @Test
    @DisplayName("Test the valid operation of delete employee")
    public void givenValidEmployeeId_whenDeleteEmployee_thenEmployeeIsDeleted() {

        // given - precondition or setup
        employeeRepository.save(employeeCreationRequest);

        // when - action or the behaviour that we are going test

        Employee theEmployee = employeeRepository.findById(employeeCreationRequest.getId()).get();
        employeeRepository.delete(theEmployee);

        Optional<Employee> employeeResponse = employeeRepository.findById(employeeIdRequest);

        // then - verify the output
        assertThat(employeeResponse).isEmpty();
    }


    @Test
    @DisplayName("Test the valid operation of custom query using jpql with index param operation")
    public void givenValidFirstNameAndLastName_whenFindByJPQLIndexParam_thenEmployeeIsFound() {

        // given - precondition or setup
        employeeRepository.save(employeeCreationRequest);

        // when - action or the behaviour that we are going test
        Employee employeeResponse = employeeRepository
                .findByJPQLIndexParam(employeeCreationRequest.getFirstName(), employeeCreationRequest.getLastName());

        // then - verify the output
        assertThat(employeeResponse).isNotNull();
    }

    @Test
    @DisplayName("Test the valid operation of custom query using jpql with named param operation")
    public void givenValidFirstNameAndLastName_whenFindByJPQLNamedParam_thenEmployeeIsFound() {

        // given - precondition or setup

        employeeRepository.save(employeeCreationRequest);

        // when - action or the behaviour that we are going test
        Employee employeeResponse = employeeRepository
                .findByJPQLNamedParam(employeeCreationRequest.getFirstName(), employeeCreationRequest.getLastName());

        // then - verify the output
        assertThat(employeeResponse).isNotNull();
    }

    @Test
    @DisplayName("Test the valid operation of custom query using native SQL with index param operation")
    public void givenValidFirstNameAndLastName_whenFindByNativeSQLIndexParam_thenEmployeeIsFound() {

        // given - precondition or setup
        employeeRepository.save(employeeCreationRequest);

        // when - action or the behaviour that we are going test
        Employee employeeResponse = employeeRepository
                .findByNativeSQLIndexParam(employeeCreationRequest.getFirstName(), employeeCreationRequest.getLastName());

        // then - verify the output
        assertThat(employeeResponse).isNotNull();
    }

    @Test
    @DisplayName("Test the valid operation of custom query using native SQL with named param operation")
    public void givenValidFirstNameAndLastName_whenFindByNativeSQLNamedParam_thenEmployeeIsFound() {

        // given - precondition or setup
        employeeRepository.save(employeeCreationRequest);

        // when - action or the behaviour that we are going test
        Employee employeeResponse = employeeRepository
                .findByNativeSQLNamedParam(employeeCreationRequest.getFirstName(), employeeCreationRequest.getLastName());

        // then - verify the output
        assertThat(employeeResponse).isNotNull();
    }
}
