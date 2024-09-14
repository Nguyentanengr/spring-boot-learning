package com.anonymous.login_web_application.controller;

import com.anonymous.login_web_application.dto.request.UserCreationRequest;
import com.anonymous.login_web_application.dto.response.PermissionResponse;
import com.anonymous.login_web_application.dto.response.RoleResponse;
import com.anonymous.login_web_application.dto.response.UserResponse;
import com.anonymous.login_web_application.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc // Ho tro request server ma khong can khoi dong toan bo server tren ung dung
                      // Ho tro tao request va kiem thu cac response
@TestPropertySource("/test.properties")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;  // Ho tro tao request va kiem thu cac response

    @MockBean
    private UserService userService;

    private UserCreationRequest request;

    private UserResponse response;

    @BeforeEach
    void initialize() {

        // generate request
        LocalDate dayOfBirth = LocalDate.of(2000, 12, 1);
        request = UserCreationRequest.builder()
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .dayOfBirth(dayOfBirth)
                .password("12345678")
                .roles(new HashSet<>(List.of("USER")))
                .build();

        // generate response

        PermissionResponse permissionResponse = PermissionResponse.builder()
                .name("CREATE_DATA")
                .description("Create data")
                .build();
        HashSet<PermissionResponse> permissionResponses = new HashSet<>();
        permissionResponses.add(permissionResponse);

        RoleResponse roleResponse = RoleResponse.builder()
                .name("USER")
                .description("Role user")
                .permissions(permissionResponses)
                .build();
        HashSet<RoleResponse> roleResponses = new HashSet<>();
        roleResponses.add(roleResponse);

        response = UserResponse.builder()
                .id(6L)
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .dayOfBirth(dayOfBirth)
                .roles(roleResponses)
                .build();
    }


    @Test
    void createUser() throws Exception {

        // (Input -> Logic -> Mock Output) ----> (Mock request -> Value) ------> (Check Value (thong qua mockMvc))
        //            GIVEN                              WHEN              THEN

        // GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);

        Mockito.when(userService.createUser(ArgumentMatchers.any())).thenReturn(response);

        // WHEN
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content)) // content cua request phai o duoi dang json
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000));
    }

    @Test
    void createUser_usernameInvalid_fail() throws Exception {

        // tao request loi
        request.setUsername("jo");

        // GIVEN
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String content = objectMapper.writeValueAsString(request);

        // WHEN THEN
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1014));
    }
}
