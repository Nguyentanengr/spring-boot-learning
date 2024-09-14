package com.anonymous.login_web_application.controller;

import com.anonymous.login_web_application.configuration.Configuration;
import com.anonymous.login_web_application.dto.request.UserCreationRequest;
import com.anonymous.login_web_application.dto.request.UserUpdateRequest;
import com.anonymous.login_web_application.dto.response.PermissionResponse;
import com.anonymous.login_web_application.dto.response.RoleResponse;
import com.anonymous.login_web_application.dto.response.UserResponse;
import com.anonymous.login_web_application.exception.AppException;
import com.anonymous.login_web_application.exception.ErrorCode;
import com.anonymous.login_web_application.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@SpringBootTest
//@AutoConfigureMockMvc

@WebMvcTest(UserController.class)
@ContextConfiguration(classes = Configuration.class)
@DisplayName("Class user controller")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private UserController userController;

    @Autowired
    private ObjectMapper objectMapper;

    private Long userIdGetRequest;
    private Long userIdDeleteRequest;
    private Long userIdUpdateRequest;
    private UserUpdateRequest userUpdateRequest;
    private UserCreationRequest userCreationRequest;

    private UserResponse mockUserGetResponse;
    private UserResponse mockUserCreationResponse;
    private UserResponse mockUserUpdateResponse;
    private List<UserResponse> mockUserGetResponses;

    // 1. Test get user by id
    @Test
    @DisplayName("Method get user by id in case request valid")
    void getUserById_whenRequestValid_returnUserResponse() throws Exception {
        when(userService.getUserById(any())).thenReturn(mockUserGetResponse);
        mockMvc.perform(get("/users/{id}", userIdGetRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1000))
                .andExpect(jsonPath("$.value.id").value(mockUserGetResponse.getId()))
                .andExpect(jsonPath("$.value.username").value(mockUserGetResponse.getUsername()));
    }

    @Test
    @DisplayName("Method get user by id in case user id invalid")
    void getUserById_WhenRequestIdInvalid_returnErrorDetails() throws Exception {
        userIdGetRequest = -1L;
        mockMvc.perform(get("/users/{id}", userIdGetRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.USER_ID_INVALID.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.USER_ID_INVALID.getMessage()));

    }

    @Test
    @DisplayName("Method get user by id in case user id not exists")
    void getUserById_WhenUserIdNotExists_returnErrorDetails() throws Exception {
        userIdGetRequest = 1000L;

        when(userService.getUserById(any())).thenThrow(new AppException(ErrorCode.USER_NOT_FOUND));

        mockMvc.perform(get("/users/{id}", userIdGetRequest))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(ErrorCode.USER_NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.USER_NOT_FOUND.getMessage()));
    }

    // 2. Test get all user
    @Test
    @DisplayName("Method get all user in case request valid")
    void getAllUser_requestValid_returnUserResponseList() throws Exception {
        when(userService.getAllUsers()).thenReturn(mockUserGetResponses);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1000))
                .andExpect(jsonPath("$.value.length()").value(mockUserGetResponses.size()));
    }

    // 3. Test create user
    @Test
    @DisplayName("Method create user in case request valid")
    void createUser_requestValid_returnUserResponse() throws Exception {

        when(userService.createUser(any())).thenReturn(mockUserCreationResponse);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userCreationRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1000))
                .andExpect(jsonPath("$.value.id").value(mockUserCreationResponse.getId()))
                .andExpect(jsonPath("$.value.username").value(mockUserCreationResponse.getUsername()));
    }


    @Test
    @DisplayName("Method create user in case roles are empty")
    void createUser_WhenRolesIsEmpty_returnErrorDetails() throws Exception {
        userCreationRequest.setRoles(null);
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userCreationRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.USER_ROLES_INVALID_EMPTY.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.USER_ROLES_INVALID_EMPTY.getMessage()));

    }

    @Test
    @DisplayName("Method create user in case username already exists")
    void createUser_WhenUsernameAlreadyExists_returnErrorDetails() throws Exception {
        userIdGetRequest = 1000L;

        when(userService.createUser(any())).thenThrow(new AppException(ErrorCode.USER_EXISTED));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userCreationRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.USER_EXISTED.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.USER_EXISTED.getMessage()));
    }

    // 4. Test update user
    @Test
    @DisplayName("Method update user in case request valid")
    void updateUser_requestValid_returnUserResponse() throws Exception {

        when(userService.updateUser(any(), any())).thenReturn(mockUserUpdateResponse);

        mockMvc.perform(put("/users/{id}", userIdUpdateRequest)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userUpdateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1000))
                .andExpect(jsonPath("$.value.id").value(mockUserUpdateResponse.getId()))
                .andExpect(jsonPath("$.value.username").value(mockUserUpdateResponse.getUsername()));
    }

    @Test
    @DisplayName("Method update user in case roles are empty")
    void updateUser_WhenRoleIsEmpty_returnErrorDetails() throws Exception {
        userUpdateRequest.setRoles(null);
        mockMvc.perform(put("/users/{id}", userIdUpdateRequest)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userUpdateRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.USER_ROLES_INVALID_EMPTY.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.USER_ROLES_INVALID_EMPTY.getMessage()));

    }

    @Test
    @DisplayName("Method update user in case user id not exists")
    void updateUser_WhenUserIdNotExists_returnErrorDetails() throws Exception {
        userIdUpdateRequest = 1000L;

        when(userService.updateUser(any(), any())).thenThrow(new AppException(ErrorCode.USER_NOT_FOUND));

        mockMvc.perform(put("/users/{id}", userIdUpdateRequest)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userUpdateRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(ErrorCode.USER_NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.USER_NOT_FOUND.getMessage()));
    }

    @Test
    @DisplayName("Method update user in case username invalid to update")
    void updateUser_WhenUsernameInvalidToUpdate_returnErrorDetails() throws Exception {

        when(userService.updateUser(any(), any())).thenThrow(new AppException(ErrorCode.USERNAME_EXISTED));

        mockMvc.perform(put("/users/{id}", userIdUpdateRequest)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(userUpdateRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(ErrorCode.USERNAME_EXISTED.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.USERNAME_EXISTED.getMessage()));
    }

    // 5. Test delete user
    @Test
    @DisplayName("Method delete user in case request valid")
    void deleteUser_requestValid_returnMessage() throws Exception {

        doNothing().when(userService).deleteUser(userIdDeleteRequest);

        mockMvc.perform(delete("/users/{id}", userIdDeleteRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1000));
    }

    @Test
    @DisplayName("Method delete user in case user id not exists")
    void deleteUser_WhenUserIdNotExists_returnErrorDetails() throws Exception {
        userIdDeleteRequest = 1000L;

        doThrow(new AppException(ErrorCode.USER_NOT_FOUND)).when(userService).deleteUser(userIdDeleteRequest);

        mockMvc.perform(delete("/users/{id}", userIdDeleteRequest))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value(ErrorCode.USER_NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.USER_NOT_FOUND.getMessage()));
    }

    // 6. Test get my info
    @Test
    @DisplayName("Method get my info in case request valid")
    void getMyInfo_requestValid_returnUserResponse() throws Exception {

        when(userService.getMyInfo()).thenReturn(mockUserGetResponse);
        mockMvc.perform(get("/users/myInfo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(1000))
                .andExpect(jsonPath("$.value.id").value(mockUserGetResponse.getId()))
                .andExpect(jsonPath("$.value.username").value(mockUserGetResponse.getUsername()));
    }

    // initialize
    @BeforeEach
    void initialize() {

        PermissionResponse mockPermissionResponse1 = PermissionResponse.builder()
                .name("APPROVE_POST")
                .description("Approve a post")
                .build();

        PermissionResponse mockPermissionResponse2 = PermissionResponse.builder()
                .name("CREATE_DATA")
                .description("Create data")
                .build();

        PermissionResponse mockPermissionResponse3 = PermissionResponse.builder()
                .name("REJECT_POST")
                .description("Reject a post")
                .build();

        RoleResponse mockRoleResponse1 = RoleResponse.builder()
                .name("ADMIN")
                .description("Role admin")
                .permissions(new HashSet<>(List.of(mockPermissionResponse1, mockPermissionResponse2, mockPermissionResponse3)))
                .build();

        RoleResponse mockRoleResponse2 = RoleResponse.builder()
                .name("USER")
                .description("Role user")
                .permissions(new HashSet<>(List.of(mockPermissionResponse2)))
                .build();

        UserResponse mockUserResponse1 = UserResponse.builder()
                .id(1L)
                .firstName(null)
                .lastName(null)
                .dayOfBirth(null)
                .username("admin")
                .roles(new HashSet<>())
                .build();

        UserResponse mockUserResponse2 = UserResponse.builder()
                .id(2L)
                .firstName("Pham Tan")
                .lastName("Nguyen")
                .dayOfBirth(LocalDate.of(2004, 10, 20))
                .username("phamtannguyen")
                .roles(new HashSet<>(List.of(mockRoleResponse2)))
                .build();

        UserResponse mockUserResponse3 = UserResponse.builder()
                .id(3L)
                .firstName("Tieu Thi")
                .lastName("Thuong")
                .dayOfBirth(LocalDate.of(2004, 11, 20))
                .username("tieuthithuong")
                .roles(new HashSet<>(List.of(mockRoleResponse2)))
                .build();


        // request
        userIdGetRequest = 2L;
        userIdUpdateRequest = 1L;
        userIdDeleteRequest = 1L;
        userCreationRequest = UserCreationRequest.builder()
                .firstName("First name")
                .lastName("Last name")
                .dayOfBirth(LocalDate.of(2004, 12, 20))
                .username("username")
                .password("password")
                .roles(new HashSet<>(List.of("USER")))
                .build();
        userUpdateRequest = UserUpdateRequest.builder()
                .firstName("Update First name") // update for user have id = 1
                .lastName("Update Last name")
                .dayOfBirth(LocalDate.of(2004, 12, 20))
                .username("update_username")
                .password("update_password")
                .roles(new HashSet<>(List.of("USER")))
                .build();

        // mock response
        mockUserGetResponse = mockUserResponse1;
        mockUserGetResponses = List.of(mockUserResponse1, mockUserResponse2, mockUserResponse3);
        mockUserCreationResponse = UserResponse.builder()
                .id(4L)
                .firstName("First name")
                .lastName("Last name")
                .dayOfBirth(LocalDate.of(2004, 12, 20))
                .username("username")
                .roles(new HashSet<>(List.of(mockRoleResponse2)))
                .build();

        mockUserUpdateResponse = UserResponse.builder()
                .id(1L)
                .firstName("Update First name") // update for user have id = 1
                .lastName("Update Last name")
                .dayOfBirth(LocalDate.of(2004, 12, 20))
                .username("update_username")
                .roles(new HashSet<>(List.of(mockRoleResponse2)))
                .build();
    }
}
