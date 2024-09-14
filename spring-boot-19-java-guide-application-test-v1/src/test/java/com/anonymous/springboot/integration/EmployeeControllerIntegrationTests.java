package com.anonymous.springboot.integration;

import com.anonymous.springboot.model.Employee;
import com.anonymous.springboot.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

import java.util.List;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerIntegrationTests extends AbstractionBaseTest{

    @Container
    private static MySQLContainer mySQLContainer = new MySQLContainer("mysql:latest")
            .withUsername("username")
            .withPassword("password")
            .withDatabaseName("ems");


    @DynamicPropertySource
    static void configureDatasource(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mySQLContainer::getUsername);
        registry.add("spring.datasource.password", mySQLContainer::getPassword);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Long employeeIdRequest;
    private Employee employeeCreationRequest;
    private Employee employeeUpdateRequest;

    private Employee mockEmployeeResponse;
    private Employee mockEmployeeCreationResponse;
    private Employee mockEmployeeUpdateResponse;
    private List<Employee> mockEmployeesResponse;

    @BeforeEach
    void initialize() {
        employeeRepository.deleteAll();

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
    @DisplayName("Test the valid operation of create employee")
    public void givenValidEmployee_whenCreateEmployee_thenEmployeeIsSaved() throws Exception {

        // when

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(employeeCreationRequest)));

        // then
        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
                        CoreMatchers.is(employeeCreationRequest.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",
                        CoreMatchers.is(employeeCreationRequest.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email",
                        CoreMatchers.is(employeeCreationRequest.getEmail())));

    }

    @Test
    @DisplayName("Test the valid operation of get all employee")
    public void givenNoThing_whenGetAll_thenEmployeeListIsFound() throws Exception {

        // given - precondition or setup
        mockEmployeesResponse.forEach(employeeRepository::save);

        // when - action or the behaviour that we are going test

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/employees"));

        // then - verify the output

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()",
                        CoreMatchers.is(mockEmployeesResponse.size())));

    }

    @Test
    @DisplayName("Test the valid operation of get employee by id")
    public void givenValidEmployeeId_whenGetById_thenEmployeeIsFound() throws Exception {

        // given - precondition or setup
        employeeRepository.save(employeeCreationRequest);

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/employees/{id}", employeeCreationRequest.getId()));

        // then - verify the output

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
                        CoreMatchers.is(mockEmployeeResponse.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",
                        CoreMatchers.is(mockEmployeeResponse.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email",
                        CoreMatchers.is(mockEmployeeResponse.getEmail())));
    }

    @Test
    @DisplayName("Test the invalid operation of get employee by id")
    public void givenInvalidEmployeeId_whenGetById_thenEmployeeIsNotFound() throws Exception {

        // given - precondition or setup
        employeeIdRequest = -1L;
        employeeRepository.save(employeeCreationRequest);

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/v1/employees/{id}",employeeIdRequest));

        // then - verify the output

        response.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        CoreMatchers.is("Employee not exists with given id")));
    }

    @Test
    @DisplayName("Test the valid operation of update employee")
    public void givenValidEmployee_whenUpdate_thenEmployeeIsUpdated() throws Exception{

        // given - precondition or setup
        employeeRepository.save(employeeCreationRequest);

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc
                .perform(MockMvcRequestBuilders.put("/api/v1/employees/{id}", employeeCreationRequest.getId())
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(employeeUpdateRequest)));

        // then - verify the output
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",
                        CoreMatchers.is(employeeUpdateRequest.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email",
                        CoreMatchers.is(employeeUpdateRequest.getEmail())));
    }

    @Test
    @DisplayName("Test the invalid operation of update employee")
    public void givenInvalidEmployee_whenUpdate_thenEmployeeIsNotUpdated() throws Exception {

        employeeIdRequest = -1L;
        // given - precondition or setup
        employeeRepository.save(employeeCreationRequest);

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc
                .perform(MockMvcRequestBuilders.put("/api/v1/employees/{id}", employeeIdRequest)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(employeeUpdateRequest)));

        // then
        response.andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$",
                        CoreMatchers.is("Employee not exists with given one")));

    }
}
