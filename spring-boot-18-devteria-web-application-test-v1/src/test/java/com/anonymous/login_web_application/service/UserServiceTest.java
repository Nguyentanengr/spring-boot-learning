package com.anonymous.login_web_application.service;

import com.anonymous.login_web_application.dto.request.UserCreationRequest;
import com.anonymous.login_web_application.dto.response.UserResponse;
import com.anonymous.login_web_application.entity.Permission;
import com.anonymous.login_web_application.entity.Role;
import com.anonymous.login_web_application.entity.User;
import com.anonymous.login_web_application.exception.AppException;
import com.anonymous.login_web_application.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Log4j2
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName(value = "Test user service class")
public class UserServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private UserCreationRequest request;

    private User response;

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

        // generate response from repository

        Permission permission = Permission.builder()
                .name("CREATE_DATA")
                .description("Create data")
                .build();
        HashSet<Permission> permissions = new HashSet<>();
        permissions.add(permission);

        Role role = Role.builder()
                .name("USER")
                .description("Role user")
                .permissions(permissions)
                .build();
        HashSet<Role> roles = new HashSet<>();
        roles.add(role);

        response = User.builder()
                .id(6L)
                .username("john")
                .firstName("John")
                .lastName("Doe")
                .dayOfBirth(dayOfBirth)
                .roles(roles)
                .build();
    }

    @Test
    @DisplayName(value = "Test create user method with valid user is success")
    void createUser_requestValid_success() {

        // GIVEN
        Mockito.when(userRepository.existsByUsername(ArgumentMatchers.anyString()))
                .thenReturn(false);
        Mockito.when(userRepository.save(ArgumentMatchers.any()))
                .thenReturn(response);

        // WHEN THEN
        UserResponse result = userService.createUser(request);

        Assertions.assertThat(result.getId()).isEqualTo(response.getId());
        Assertions.assertThat(result.getUsername()).isEqualTo(response.getUsername());
    }

    @DisplayName(value = "Test create user method with user existed is fail")
    @RepeatedTest(value = 10, name = "Repetition {currentRepetition} / {totalRepetitions}")
    void createUser_userExisted_fail() {

        log.info("For repeated test");
        request.setUsername("phamtannguyen");

        // GIVEN
        Mockito.when(userRepository.existsByUsername(ArgumentMatchers.anyString()))
                .thenReturn(true);

        AppException e = assertThrows(AppException.class, () -> userService.createUser(request));

        Assertions.assertThat(e.getErrorCode().getCode()).isEqualTo(1005L);
        Assertions.assertThat(e.getErrorCode().getMessage()).isEqualTo("Username already exists");


    }

    @DisplayName(value = "Test create user method with invalid list of user roles and fails")
    @RepeatedTest(value = 10, name = "Repetition {currentRepetition} / {totalRepetitions}")
    void createUser_listOfUserRolesInvalid_fail() {

        log.info("For repeated test");
        request.setRoles(new HashSet<>(List.of("USE")));

        // GIVEN
        Mockito.when(userRepository.existsByUsername(ArgumentMatchers.anyString()))
                .thenReturn(false);

        AppException e = assertThrows(AppException.class, () -> userService.createUser(request));

        Assertions.assertThat(e.getErrorCode().getCode()).isEqualTo(1025L);
        Assertions.assertThat(e.getErrorCode().getMessage()).isEqualTo("Role not found");

    }

    @Test
    @WithMockUser(username = "john")
    void getMyInfo_valid_success() {

        Mockito.when(userRepository.findByUsername(ArgumentMatchers.anyString()))
                .thenReturn(Optional.of(response));

        UserResponse result = userService.getMyInfo();

        Assertions.assertThat(result.getUsername()).isEqualTo(response.getUsername());
        Assertions.assertThat(result.getId()).isEqualTo(response.getId());
    }

    @Test
    @WithMockUser(username = "john")
    void getMyInfo_userNotFound_fail() {
        Mockito.when(userRepository.findByUsername(ArgumentMatchers.anyString()))
                .thenReturn(Optional.ofNullable(null));

        AppException exception = assertThrows(AppException.class, () -> userService.getMyInfo());

        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1002);
        Assertions.assertThat(exception.getErrorCode().getMessage()).isEqualTo("User not found");
    }
}
