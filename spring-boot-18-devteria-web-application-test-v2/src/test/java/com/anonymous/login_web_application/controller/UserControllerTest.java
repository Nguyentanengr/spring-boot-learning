package com.anonymous.login_web_application.controller;


import com.anonymous.login_web_application.config.Configuration;
import com.anonymous.login_web_application.dto.request.UserCreationRequest;
import com.anonymous.login_web_application.dto.request.UserUpdateRequest;
import com.anonymous.login_web_application.dto.response.ApiResponse;
import com.anonymous.login_web_application.dto.response.PermissionResponse;
import com.anonymous.login_web_application.dto.response.RoleResponse;
import com.anonymous.login_web_application.dto.response.UserResponse;
import com.anonymous.login_web_application.exception.AppException;
import com.anonymous.login_web_application.exception.ErrorCode;
import com.anonymous.login_web_application.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
@ContextConfiguration(classes = Configuration.class)
@DisplayName("Test for user controller")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private Long userIdRequest;
    private UserResponse mockUserResponse;
    private List<UserResponse> mockUsersResponse;

    private UserCreationRequest userCreationRequest;
    private UserResponse mockUserCreationResponse;

    private UserUpdateRequest userUpdateRequest;
    private UserResponse mockUserUpdateResponse;


    @BeforeEach
    public void initialize() {
        PermissionResponse permissionResponse = PermissionResponse.builder()
                .name("CREATE_DATA")
                .description("Create data")
                .build();
        RoleResponse roleResponse = RoleResponse.builder()
                .name("USER")
                .description("Role user")
                .permissions(new HashSet<>(List.of(permissionResponse)))
                .build();

        userIdRequest = 1L;
        mockUserResponse = UserResponse.builder()
                .id(1L)
                .username("john.doe")
                .firstName("John")
                .lastName("Doe")
                .dayOfBirth(LocalDate.of(2004, 10, 16))
                .roles(new HashSet<>(List.of(roleResponse)))
                .build();
        mockUsersResponse = List.of(mockUserResponse);

        userCreationRequest = UserCreationRequest.builder()
                .username("john.doe")
                .password("10000000")
                .firstName("John")
                .lastName("Doe")
                .dayOfBirth(LocalDate.of(2004, 10, 16))
                .roles(new HashSet<>(List.of("USER")))
                .build();
        mockUserCreationResponse = UserResponse.builder()
                .id(1L)
                .username("john.doe")
                .firstName("John")
                .lastName("Doe")
                .dayOfBirth(LocalDate.of(2004, 10, 16))
                .roles(new HashSet<>(List.of(roleResponse)))
                .build();

        userUpdateRequest = UserUpdateRequest.builder()
                .id(1L)
                .username("john.cena")
                .password("10000000")
                .firstName("John")
                .lastName("Cena")
                .dayOfBirth(LocalDate.of(2004, 10, 16))
                .roles(new HashSet<>(List.of("USER")))
                .build();
        mockUserUpdateResponse = UserResponse.builder()
                .id(1L)
                .username("john.cena")
                .firstName("John")
                .lastName("Cena")
                .dayOfBirth(LocalDate.of(2004, 10, 16))
                .roles(new HashSet<>(List.of(roleResponse)))
                .build();
    }


    @Test
    @DisplayName("Test the operation of get user by id with valid id")
    public void givenValidUser_whenGetById_thenUserIsFound() throws Exception {

        // given - precondition or setup
        when(userService.getUserById(anyLong())).thenReturn(mockUserResponse);

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/users/{id}", userIdRequest));

        // then - verify the output
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1000))
                .andExpect(jsonPath("$.code").value(1000))
                .andExpect(jsonPath("$.value.id").value(userIdRequest))
                .andExpect(jsonPath("$.value.username").value(mockUserResponse.getUsername()))
                .andExpect(jsonPath("$.value.firstName").value(mockUserResponse.getFirstName()))
                .andExpect(jsonPath("$.value.lastName").value(mockUserResponse.getLastName()))
                .andExpect(jsonPath("$.value.roles").value(objectMapper.convertValue(mockUserResponse.getRoles(), List.class)));

    }

    @Test
    @DisplayName("Test the operation of get user by id with invalid id")
    public void givenInvalidUserId_whenGetById_thenUserIsNotFound() throws Exception {

        // given - precondition or setup
        userIdRequest = -1L;

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/users/{id}", userIdRequest));

        // then - verify the output
        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.USER_ID_INVALID.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.USER_ID_INVALID.getMessage()));
    }

    @Test
    @DisplayName("Test the operation of get user by id with non-existent id")
    public void givenNonExistentUserId_whenGetById_thenUserIsNotFound() throws Exception {

        // given - precondition or setup
        when(userService.getUserById(anyLong())).thenThrow(new AppException(ErrorCode.USER_NOT_FOUND));

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/users/{id}", userIdRequest));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(ErrorCode.USER_NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.USER_NOT_FOUND.getMessage()));
    }

    @Test
    @DisplayName("Test the operation of get all users")
    public void givenNoThing_whenGetAll_thenUserListIsFound() throws Exception {

        // given - precondition or setup
        when(userService.getAllUsers()).thenReturn(mockUsersResponse);

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/users"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1000))
                .andExpect(content().json(objectMapper.writeValueAsString(
                        ApiResponse.<List<UserResponse>>builder()
                                .value(mockUsersResponse)
                                .build()
                )));

    }

    @Test
    @DisplayName("Test the operation of create user with valid fields")
    public void givenValidUser_whenCreate_thenUserIsCreated() throws Exception {

        // given - precondition or setup
        when(userService.createUser(any(UserCreationRequest.class))).thenReturn(mockUserCreationResponse);

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userCreationRequest)));

        // then - verify the output
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1000))
                .andExpect(jsonPath("$.value.id").value(mockUserCreationResponse.getId()))
                .andExpect(jsonPath("$.value.username").value(mockUserCreationResponse.getUsername()))
                .andExpect(jsonPath("$.value.firstName").value(mockUserCreationResponse.getFirstName()))
                .andExpect(jsonPath("$.value.lastName").value(mockUserCreationResponse.getLastName()))
                .andExpect(jsonPath("$.value.roles")
                        .value(objectMapper.convertValue(mockUserCreationResponse.getRoles(), List.class)));

    }

    @Test
    @DisplayName("Test the operation of create user with invalid fields")
    public void givenInvalidUser_whenCreate_thenUserIsNotCreated() throws Exception {

        // given - precondition or setup
        userCreationRequest.setRoles(null);

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userCreationRequest)));

        // then - verify the output
        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.USER_ROLES_INVALID_EMPTY.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.USER_ROLES_INVALID_EMPTY.getMessage()));

    }

    @Test
    @DisplayName("Test the operation of create user with already username")
    public void givenAlreadyUsername_whenCreate_thenUserIsNotCreated() throws Exception {

        // given - precondition or setup
        when(userService.createUser(any(UserCreationRequest.class)))
                .thenThrow(new AppException(ErrorCode.USERNAME_EXISTED));

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userCreationRequest)));

        // then - verify the output
        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.USERNAME_EXISTED.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.USERNAME_EXISTED.getMessage()));

    }

    @Test
    @DisplayName("Test the operation of update user with valid fields")
    public void givenValidUser_whenUpdate_thenUserIsUpdated() throws Exception{

        // given - precondition or setup
        when(userService.updateUser(any(UserUpdateRequest.class), anyLong()))
                .thenReturn(mockUserUpdateResponse);

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/users/{id}", userUpdateRequest.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userUpdateRequest)));

        // then - verify the output
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1000))
                .andExpect(jsonPath("$.value.id").value(mockUserUpdateResponse.getId()))
                .andExpect(jsonPath("$.value.username").value(mockUserUpdateResponse.getUsername()))
                .andExpect(jsonPath("$.value.firstName").value(mockUserUpdateResponse.getFirstName()))
                .andExpect(jsonPath("$.value.lastName").value(mockUserUpdateResponse.getLastName()))
                .andExpect(jsonPath("$.value.roles")
                        .value(objectMapper.convertValue(mockUserUpdateResponse.getRoles(), List.class)));

    }

    @Test
    @DisplayName("Test the operation of update user with invalid fields")
    public void givenInvalidUserId_whenUpdate_thenUserIsNotUpdated() throws Exception{

        // given - precondition or setup

        userUpdateRequest.setId(-1L);
        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/users/{id}", userUpdateRequest.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userUpdateRequest)));

        // then - verify the output

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.USER_ID_INVALID.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.USER_ID_INVALID.getMessage()));
    }

    @Test
    @DisplayName("Test the operation of update user with non-existent id")
    public void givenNonExistentUserId_whenUpdate_thenUserIsNotUpdated() throws Exception{

        // given - precondition or setup
        when(userService.updateUser(any(UserUpdateRequest.class), anyLong()))
                .thenThrow(new AppException(ErrorCode.USER_NOT_FOUND));

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/users/{id}", userUpdateRequest.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userUpdateRequest)));

        // then - verify the output

        response.andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(ErrorCode.USER_NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.USER_NOT_FOUND.getMessage()));
    }

    @Test
    @DisplayName("Test the operation of update user with already exists username")
    public void givenAlreadyExistsUsername_whenUpdate_thenUserIsNotUpdated() throws Exception{

        // given - precondition or setup
        when(userService.updateUser(any(UserUpdateRequest.class), anyLong()))
                .thenThrow(new AppException(ErrorCode.USERNAME_EXISTED));

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/users/{id}", userUpdateRequest.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userUpdateRequest)));

        // then - verify the output

        response.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.USERNAME_EXISTED.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.USERNAME_EXISTED.getMessage()));
    }

    @Test
    @DisplayName("Test the operation of get my info")
    public void givenNoThing_whenGetMyInfo_thenUserIsFound() throws Exception{

        // given - precondition or setup
        when(userService.getMyInfo()).thenReturn(mockUserCreationResponse);

        // when - action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(get("/users/myInfo"));

        // then - verify the output
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1000))
                .andExpect(jsonPath("$.value.id").value(mockUserCreationResponse.getId()))
                .andExpect(jsonPath("$.value.username").value(mockUserCreationResponse.getUsername()))
                .andExpect(jsonPath("$.value.firstName").value(mockUserCreationResponse.getFirstName()))
                .andExpect(jsonPath("$.value.lastName").value(mockUserCreationResponse.getLastName()))
                .andExpect(jsonPath("$.value.roles")
                        .value(objectMapper.convertValue(mockUserCreationResponse.getRoles(), List.class)));
    }


}
