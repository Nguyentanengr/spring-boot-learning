package com.anonymous.login_web_application.service;

import com.anonymous.login_web_application.dto.request.UserCreationRequest;
import com.anonymous.login_web_application.dto.response.RoleResponse;
import com.anonymous.login_web_application.dto.response.UserResponse;
import com.anonymous.login_web_application.entity.Permission;
import com.anonymous.login_web_application.entity.Role;
import com.anonymous.login_web_application.entity.User;
import com.anonymous.login_web_application.exception.AppException;
import com.anonymous.login_web_application.exception.ErrorCode;
import com.anonymous.login_web_application.mapper.RoleMapper;
import com.anonymous.login_web_application.mapper.UserMapper;
import com.anonymous.login_web_application.repository.RoleRepository;
import com.anonymous.login_web_application.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@Log4j2
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Class user service")
public class UserServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserMapper userMapper;


    private Long userIdGetRequest;
    private User mockUserGetResponse;

    private UserCreationRequest userCreationRequest;
    private User mockUserCreationResponse;

    private List<User> mockUserGetResponses;
    private List<Role> mockRoles;

    // 1. Test get user by id
    @Test
    @DisplayName("Method get user by id in case request valid")
    void getUserById_whenRequestValid_returnUserResponse() {
        // khoi tao
        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(mockUserGetResponse));

        UserResponse userResponse = userService.getUserById(userIdGetRequest);

        assertThat(userResponse.getId()).isEqualTo(mockUserGetResponse.getId());
        assertThat(userResponse.getUsername()).isEqualTo(mockUserGetResponse.getUsername());
        assertThat(userResponse.getFirstName()).isEqualTo(mockUserGetResponse.getFirstName());
        assertThat(userResponse.getLastName()).isEqualTo(mockUserGetResponse.getLastName());
        assertThat(userResponse.getDayOfBirth()).isEqualTo(mockUserGetResponse.getDayOfBirth());
        assertThat(userResponse.getRoles().size()).isEqualTo(mockUserGetResponse.getRoles().size());
    }

    @Test
    @DisplayName("Method get user by id in case user id not exist")
    void getUserById_whenUserIdNotExists_returnErrorDetails() {

        userIdGetRequest = 1000L;

        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(null));

        AppException exception = assertThrows(AppException.class, () -> userService.getUserById(userIdGetRequest));

        assertThat(exception.getErrorCode().getHttpStatusCode()).isEqualTo(ErrorCode.USER_NOT_FOUND.getHttpStatusCode());
        assertThat(exception.getErrorCode().getCode()).isEqualTo(ErrorCode.USER_NOT_FOUND.getCode());
        assertThat(exception.getErrorCode().getMessage()).isEqualTo(ErrorCode.USER_NOT_FOUND.getMessage());
    }

    // Test get all user
    @Test
    @DisplayName("Method get all user in case request valid")
    void getAllUser_whenRequestValid_returnListUserResponse() {

        when(userRepository.findAll()).thenReturn(mockUserGetResponses);


        List<UserResponse> results = userService.getAllUsers();
        List<UserResponse> userResponses = mockUserGetResponses
                .stream()
                .map(userMapper::mapToUserResponse)
                .collect(Collectors.toList());

        assertThat(results).isEqualTo(userResponses);
    }

    @Test
    @DisplayName("Method create user in case request valid")
    void createUser_whenRequestValid_returnUserResponse() {

        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(roleRepository.findAllById(anyCollection())).thenReturn(mockRoles);
        when(userRepository.save(any(User.class))).thenReturn(mockUserCreationResponse);

        UserResponse userResponse = userService.createUser(userCreationRequest);

        Set<RoleResponse> roleResponses = mockUserCreationResponse.getRoles()
                .stream().map(roleMapper::mapToRoleResponse).collect(Collectors.toSet());

        assertThat(userResponse.getId()).isEqualTo(mockUserCreationResponse.getId());
        assertThat(userResponse.getFirstName()).isEqualTo(mockUserCreationResponse.getFirstName());
        assertThat(userResponse.getLastName()).isEqualTo(mockUserCreationResponse.getLastName());
        assertThat(userResponse.getDayOfBirth()).isEqualTo(mockUserCreationResponse.getDayOfBirth());
        assertThat(userResponse.getUsername()).isEqualTo(mockUserCreationResponse.getUsername());
        assertThat(userResponse.getRoles()).isEqualTo(roleResponses);
    }

    @BeforeEach
    void initialize() {
        Permission mockPermission1 = Permission.builder()
                .name("APPROVE_POST")
                .description("Approve a post")
                .build();

        Permission mockPermission2 = Permission.builder()
                .name("CREATE_DATA")
                .description("Create data")
                .build();

        Permission mockPermission3 = Permission.builder()
                .name("REJECT_POST")
                .description("Reject a post")
                .build();

        Role mockRole1 = Role.builder()
                .name("ADMIN")
                .description("Role admin")
                .permissions(new HashSet<>(List.of(mockPermission1, mockPermission2, mockPermission3)))
                .build();

        Role mockRole2 = Role.builder()
                .name("USER")
                .description("Role user")
                .permissions(new HashSet<>(List.of(mockPermission2)))
                .build();

        User mockUser1 = User.builder()
                .id(1L)
                .firstName(null)
                .lastName(null)
                .dayOfBirth(null)
                .username("admin")
                .roles(new HashSet<>())
                .build();

        User mockUser2 = User.builder()
                .id(2L)
                .firstName("Pham Tan")
                .lastName("Nguyen")
                .dayOfBirth(LocalDate.of(2004, 10, 20))
                .username("phamtannguyen")
                .roles(new HashSet<>(List.of(mockRole2)))
                .build();

        User mockUser3 = User.builder()
                .id(3L)
                .firstName("Tieu Thi")
                .lastName("Thuong")
                .dayOfBirth(LocalDate.of(2004, 11, 20))
                .username("tieuthithuong")
                .roles(new HashSet<>(List.of(mockRole2)))
                .build();

        userCreationRequest = UserCreationRequest.builder()
                .firstName("First name")
                .lastName("Last name")
                .dayOfBirth(LocalDate.of(2004, 12, 20))
                .username("username")
                .password("password")
                .roles(new HashSet<>(List.of("USER")))
                .build();

        mockUserCreationResponse = User.builder()
                .id(4L)
                .firstName("First name")
                .lastName("Last name")
                .dayOfBirth(LocalDate.of(2004, 12, 20))
                .username("username")
                .password("password")
                .roles(new HashSet<>(List.of(mockRole2)))
                .build();

        userIdGetRequest = 2L;
        mockUserGetResponse = mockUser2;
        mockUserGetResponses = List.of(mockUser1, mockUser2, mockUser3);
        mockRoles = List.of(mockRole2);


    }
}
