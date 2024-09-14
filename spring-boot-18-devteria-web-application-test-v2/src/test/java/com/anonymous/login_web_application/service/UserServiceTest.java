package com.anonymous.login_web_application.service;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import com.anonymous.login_web_application.dto.request.UserCreationRequest;
import com.anonymous.login_web_application.dto.request.UserUpdateRequest;
import com.anonymous.login_web_application.dto.response.PermissionResponse;
import com.anonymous.login_web_application.dto.response.RoleResponse;
import com.anonymous.login_web_application.dto.response.UserResponse;
import com.anonymous.login_web_application.entity.Permission;
import com.anonymous.login_web_application.entity.Role;
import com.anonymous.login_web_application.entity.User;
import com.anonymous.login_web_application.exception.AppException;
import com.anonymous.login_web_application.exception.ErrorCode;
import com.anonymous.login_web_application.repository.RoleRepository;
import com.anonymous.login_web_application.repository.UserRepository;

@SpringBootTest
@DisplayName("Test for user service")
public class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;

    private List<Role> mockRoles;

    private Long userIdRequest;
    private User mockUserResponse;
    private UserResponse checkUserResponse;

    private UserCreationRequest userCreationRequest;
    private User mockUserCreationResponse;
    private UserResponse checkUserCreationResponse;

    private List<User> mockUsersResponse;
    private List<UserResponse> checkUsersResponse;

    private UserUpdateRequest userUpdateRequest;
    private User mockUserUpdateResponse;
    private UserResponse checkUserUpdateResponse;

    @BeforeEach
    public void initialize() {
        Permission permission = Permission.builder()
                .name("CREATE_DATA")
                .description("Create data")
                .build();
        PermissionResponse permissionResponse = PermissionResponse.builder()
                .name("CREATE_DATA")
                .description("Create data")
                .build();

        Role role = Role.builder()
                .name("USER")
                .description("Role user")
                .permissions(new HashSet<>(List.of(permission)))
                .build();
        RoleResponse roleResponse = RoleResponse.builder()
                .name("USER")
                .description("Role user")
                .permissions(new HashSet<>(List.of(permissionResponse)))
                .build();

        mockRoles = List.of(role);

        userIdRequest = 1L;
        mockUserResponse = User.builder()
                .id(1L)
                .username("john.doe")
                .password("$2a$10$k11doVxlrmZyeIKbxKyyy.GBFMvmgtX1Q0SIsiZYoSQP/CEXWaKFi")
                .firstName("John")
                .lastName("Doe")
                .dayOfBirth(LocalDate.of(2004, 10, 16))
                .roles(new HashSet<>(List.of(role)))
                .build();
        checkUserResponse = UserResponse.builder()
                .id(1L)
                .username("john.doe")
                .firstName("John")
                .lastName("Doe")
                .dayOfBirth(LocalDate.of(2004, 10, 16))
                .roles(new HashSet<>(List.of(roleResponse)))
                .build();

        userCreationRequest = UserCreationRequest.builder()
                .username("john.doe")
                .password("10000000")
                .firstName("John")
                .lastName("Doe")
                .dayOfBirth(LocalDate.of(2004, 10, 16))
                .roles(new HashSet<>(List.of("USER")))
                .build();
        mockUserCreationResponse = User.builder()
                .id(1L)
                .username("john.doe")
                .password("$2a$10$k11doVxlrmZyeIKbxKyyy.GBFMvmgtX1Q0SIsiZYoSQP/CEXWaKFi")
                .firstName("John")
                .lastName("Doe")
                .dayOfBirth(LocalDate.of(2004, 10, 16))
                .roles(new HashSet<>(List.of(role)))
                .build();
        checkUserCreationResponse = UserResponse.builder()
                .id(1L)
                .username("john.doe")
                .firstName("John")
                .lastName("Doe")
                .dayOfBirth(LocalDate.of(2004, 10, 16))
                .roles(new HashSet<>(List.of(roleResponse)))
                .build();

        mockUsersResponse = List.of(mockUserResponse);
        checkUsersResponse = List.of(checkUserResponse);

        userUpdateRequest = UserUpdateRequest.builder()
                .id(1L)
                .username("john.cena")
                .password("10000000")
                .firstName("John")
                .lastName("Cena")
                .dayOfBirth(LocalDate.of(2004, 10, 16))
                .roles(new HashSet<>(List.of("USER")))
                .build();

        mockUserUpdateResponse = User.builder()
                .id(1L)
                .username("john.cena")
                .password("$2a$10$k11doVxlrmZyeIKbxKyyy.GBFMvmgtX1Q0SIsiZYoSQP/CEXWaKFi")
                .firstName("John")
                .lastName("Cena")
                .dayOfBirth(LocalDate.of(2004, 10, 16))
                .roles(new HashSet<>(List.of(role)))
                .build();

        checkUserUpdateResponse = UserResponse.builder()
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
    public void givenValidUserId_whenGetById_thenUserIsFound() {
        // given - precondition or setup
        when(userRepository.findById(any())).thenReturn(Optional.of(mockUserResponse));

        // when - action or the behaviour that we are going test
        UserResponse userResponse = userService.getUserById(userIdRequest);

        // then - verify the output
        assertThat(userResponse).isNotNull();
        assertThat(userResponse.getId()).isEqualTo(checkUserResponse.getId());
        assertThat(userResponse.getUsername()).isEqualTo(checkUserResponse.getUsername());
        assertThat(userResponse.getRoles()).isEqualTo(checkUserResponse.getRoles());
    }

    @Test
    @DisplayName("Test the operation of get user by id with non-existent id")
    public void givenNonExistentUserId_whenGetById_thenUserIsNotFound() {
        // given - precondition or setup
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        // when - action or the behaviour that we are going test
        AppException exception = assertThrows(AppException.class, () -> userService.getUserById(any()));

        // then - verify the output
        assertThat(exception).isNotNull();
        assertThat(exception.getErrorCode().getCode()).isEqualTo(ErrorCode.USER_NOT_FOUND.getCode());
        assertThat(exception.getErrorCode().getMessage()).isEqualTo(ErrorCode.USER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("Test the operation of get alls user")
    public void givenNoThing_whenGetAll_thenUserListIsFound() {

        // given - precondition or setup
        when(userRepository.findAll()).thenReturn(mockUsersResponse);

        // when - action or the behaviour that we are going test
        List<UserResponse> usersResponse = userService.getAllUsers();

        // then - verify the output
        assertThat(usersResponse).isNotNull();
        assertThat(usersResponse).isEqualTo(checkUsersResponse);
        assertThat(usersResponse.size()).isEqualTo(checkUsersResponse.size());
    }

    @Test
    @DisplayName("Test the operation of create user with valid fields")
    public void givenValidUser_whenCreate_thenUserIsCreated() {

        // given - precondition or setup
        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(mockUserCreationResponse);
        when(roleRepository.findAllById(anyCollection())).thenReturn(mockRoles);

        // when - action or the behaviour that we are going test
        UserResponse userResponse = userService.createUser(userCreationRequest);

        // then - verify the output
        assertThat(userResponse).isNotNull();
        assertThat(userResponse.getId()).isEqualTo(checkUserCreationResponse.getId());
        assertThat(userResponse.getUsername()).isEqualTo(checkUserCreationResponse.getUsername());
        assertThat(userResponse.getRoles()).isEqualTo(checkUserCreationResponse.getRoles());
    }

    @Test
    @DisplayName("Test the operation of create user with already exists username")
    public void givenAlreadyExistsUsername_whenCreate_thenUserIsNotCreated() {

        // given - precondition or setup
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        // when - action or the behaviour that we are going test
        AppException exception = assertThrows(AppException.class, () -> userService.createUser(userCreationRequest));

        // then - verify the output
        verify(roleRepository, never()).findAllById(anyCollection());
        verify(userRepository, never()).save(any(User.class));

        assertThat(exception).isNotNull();
        assertThat(exception.getErrorCode().getCode()).isEqualTo(ErrorCode.USERNAME_EXISTED.getCode());
        assertThat(exception.getErrorCode().getMessage()).isEqualTo(ErrorCode.USERNAME_EXISTED.getMessage());
    }

    @Test
    @DisplayName("Test the operation of update user with valid fields")
    public void givenValidUser_whenUpdate_thenUserIsUpdated() {

        // given - precondition or setup
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mockUserResponse));
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(roleRepository.findAllById(anyCollection())).thenReturn(mockRoles);
        when(userRepository.save(any(User.class))).thenReturn(mockUserUpdateResponse);

        // when - action or the behaviour that we are going test
        UserResponse userResponse = userService.updateUser(userUpdateRequest, userUpdateRequest.getId());

        // then - verify the output
        assertThat(mockUserResponse.getId()).isEqualTo(mockUserUpdateResponse.getId());
        assertThat(mockUserResponse.getUsername()).isEqualTo(mockUserUpdateResponse.getUsername());
        assertThat(mockUserResponse.getRoles()).isEqualTo(mockUserUpdateResponse.getRoles());

        assertThat(userResponse).isNotNull();
        assertThat(userResponse.getId()).isEqualTo(checkUserUpdateResponse.getId());
        assertThat(userResponse.getUsername()).isEqualTo(checkUserUpdateResponse.getUsername());
        assertThat(userResponse.getRoles()).isEqualTo(checkUserUpdateResponse.getRoles());
    }

    @Test
    @DisplayName("Test the operation of update user with valid fields and username not update")
    public void givenValidUserAndUsernameNotUpdate_whenUpdate_thenUserIsUpdated() {

        // given - precondition or setup
        userUpdateRequest.setUsername("john.doe"); // username not altered
        mockUserUpdateResponse.setUsername("john.doe"); // mock username
        checkUserUpdateResponse.setUsername("john.doe"); // check username

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mockUserResponse));
        when(roleRepository.findAllById(anyCollection())).thenReturn(mockRoles);
        when(userRepository.save(any(User.class))).thenReturn(mockUserUpdateResponse);

        // when - action or the behaviour that we are going test
        UserResponse userResponse = userService.updateUser(userUpdateRequest, userUpdateRequest.getId());

        // then - verify the output
        assertThat(mockUserResponse.getId()).isEqualTo(mockUserUpdateResponse.getId());
        assertThat(mockUserResponse.getUsername()).isEqualTo(mockUserUpdateResponse.getUsername());
        assertThat(mockUserResponse.getRoles()).isEqualTo(mockUserUpdateResponse.getRoles());

        assertThat(userResponse).isNotNull();
        assertThat(userResponse.getId()).isEqualTo(checkUserUpdateResponse.getId());
        assertThat(userResponse.getUsername()).isEqualTo(checkUserUpdateResponse.getUsername());
        assertThat(userResponse.getRoles()).isEqualTo(checkUserUpdateResponse.getRoles());
    }

    @Test
    @DisplayName("Test the operation of update user with non-existent id")
    public void givenNonExistentUserId_whenUpdate_thenUserIsNotUpdated() {

        // given - precondition or setup
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when - action or the behaviour that we are going test
        AppException exception = assertThrows(
                AppException.class, () -> userService.updateUser(userUpdateRequest, userUpdateRequest.getId()));

        // then - verify the output
        verify(roleRepository, never()).findAllById(anyCollection());
        verify(userRepository, never()).findByUsername(anyString());
        verify(userRepository, never()).save(any(User.class));

        assertThat(exception).isNotNull();
        assertThat(exception.getErrorCode().getCode()).isEqualTo(ErrorCode.USER_NOT_FOUND.getCode());
        assertThat(exception.getErrorCode().getMessage()).isEqualTo(ErrorCode.USER_NOT_FOUND.getMessage());
    }


    @Test
    @DisplayName("Test the operation of update user with already exists username")
    public void givenAlreadyExistsUsername_whenUpdate_thenUserIsNotUpdated() {

        // given - precondition or setup
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(mockUserResponse));
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(mockUserResponse));

        // when - action or the behaviour that we are going test
        AppException exception = assertThrows(
                AppException.class, () -> userService.updateUser(userUpdateRequest, userUpdateRequest.getId()));

        // then - verify the output
        verify(roleRepository, never()).findAllById(anyCollection());
        verify(userRepository, never()).save(any(User.class));

        assertThat(exception).isNotNull();
        assertThat(exception.getErrorCode().getCode()).isEqualTo(ErrorCode.USERNAME_EXISTED.getCode());
        assertThat(exception.getErrorCode().getMessage()).isEqualTo(ErrorCode.USERNAME_EXISTED.getMessage());
    }

    // get my info
    @Test
    @WithMockUser(username = "john.doe")
    @DisplayName("Test the operation of get my info")
    public void givenNoThing_whenGetMyInfo_thenUserIsFound() {

        // given - precondition or setup
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(mockUserResponse));

        // when - action or the behaviour that we are going test
        UserResponse userResponse = userService.getMyInfo();

        // then - verify the output
        assertThat(userResponse).isNotNull();
        assertThat(userResponse.getUsername()).isEqualTo(checkUserResponse.getUsername());
        assertThat(userResponse.getRoles()).isEqualTo(checkUserResponse.getRoles());
    }

    @Test
    @WithMockUser(username = "john.doe")
    @DisplayName("Test the operation of get my info with non-existent username")
    public void givenNonExistentUsername_whenGetMyInfo_thenUserIsNotFound() {

        // given - precondition or setup
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        // when - action or the behaviour that we are going test
        AppException exception = assertThrows(AppException.class, () -> userService.getMyInfo());

        // then - verify the output
        assertThat(exception).isNotNull();
        assertThat(exception.getErrorCode().getCode()).isEqualTo(ErrorCode.USER_NOT_FOUND.getCode());
        assertThat(exception.getErrorCode().getMessage()).isEqualTo(ErrorCode.USER_NOT_FOUND.getMessage());
    }
}
