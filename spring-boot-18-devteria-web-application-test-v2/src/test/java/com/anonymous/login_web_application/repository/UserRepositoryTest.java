package com.anonymous.login_web_application.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.anonymous.login_web_application.entity.Permission;
import com.anonymous.login_web_application.entity.Role;
import com.anonymous.login_web_application.entity.User;

@DataJpaTest // use with h2-db quan li cac test voi transactional
@DisplayName("Test for user repository")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private Long userIdRequest;
    private String usernameRequest;
    private User userCreateRequest;

    @BeforeEach
    public void initialize() {

        Permission permission = Permission.builder()
                .name("CREATE_DATA")
                .description("Create data")
                .build();

        Role role = Role.builder()
                .name("USER")
                .description("Role user")
                .permissions(new HashSet<>(List.of(permission)))
                .build();

        userIdRequest = 1L;
        usernameRequest = "john.doe";
        userCreateRequest = User.builder()
                .username("john.doe")
                .password("$2a$10$k11doVxlrmZyeIKbxKyyy.GBFMvmgtX1Q0SIsiZYoSQP/CEXWaKFi")
                .firstName("John")
                .lastName("Doe")
                .dayOfBirth(LocalDate.of(2004, 10, 16))
                .roles(new HashSet<>(List.of(role)))
                .build();
    }

    @Test
    @DisplayName("Test the operation of find user by id with valid id")
    public void givenValidUserId_whenFindById_thenUserIsFound() {
        // given - precondition or setup
        userRepository.save(userCreateRequest);

        // when - action or the behaviour that we are going test
        User userResponse = userRepository.findById(userCreateRequest.getId()).get();

        // then - verify the output
        assertThat(userResponse).isNotNull();
        assertThat(userResponse.getId()).isEqualTo(userCreateRequest.getId());
    }

    @Test
    @DisplayName("Test the operation of find user by id with non-existent id")
    public void givenNonExistentUserId_whenFindById_thenUserIsNotFound() {
        // given - precondition or setup
        userIdRequest = -1L; // mock non-existent user id
        userRepository.save(userCreateRequest);

        // when - action or the behaviour that we are going test
        User userResponse = userRepository.findById(userIdRequest).orElseGet(() -> null);

        // then - verify the output
        assertThat(userResponse).isNull();
    }

    @Test
    @DisplayName("Test the operation of exists by username with already exists username")
    public void givenAlreadyExistsUsername_whenExistsByUsername_thenUserIsFound() {

        // given - precondition or setup
        userRepository.save(userCreateRequest);

        // when - action or the behaviour that we are going test
        boolean existed = userRepository.existsByUsername(userCreateRequest.getUsername());

        // then - verify the output
        assertThat(existed).isTrue();
    }

    @Test
    @DisplayName("Test the operation of exists by username with non-existent username")
    public void givenNonExistentUsername_whenExistsByUsername_thenUserIsNotFound() {

        // given - precondition or setup
        usernameRequest = "non_existent_username";
        userRepository.save(userCreateRequest);

        // when - action or the behaviour that we are going test
        boolean existed = userRepository.existsByUsername(usernameRequest);

        // then - verify the output
        assertThat(existed).isFalse();
    }

    @Test
    @DisplayName("Test the operation of find user by username with valid username")
    public void givenValidUsername_whenFindByUsername_thenUserIsFound() {
        // given - precondition or setup
        userRepository.save(userCreateRequest);

        // when - action or the behaviour that we are going test
        User userResponse =
                userRepository.findByUsername(userCreateRequest.getUsername()).get();

        // then - verify the output
        assertThat(userResponse).isNotNull();
        assertThat(userResponse.getUsername()).isEqualTo(userCreateRequest.getUsername());
    }

    @Test
    @DisplayName("Test the operation of find user by username with non-existent username")
    public void givenNonExistentUsername_whenFindByUsername_thenUserIsNotFound() {
        // given - precondition or setup
        usernameRequest = "non_existent_username";
        userRepository.save(userCreateRequest);

        // when - action or the behaviour that we are going test
        User userResponse = userRepository.findByUsername(usernameRequest).orElseGet(() -> null);

        // then - verify the output
        assertThat(userResponse).isNull();
    }

    @Test
    @DisplayName("Test the operation of find all user")
    public void givenNoThing_whenFindAll_then() {

        // given - precondition or setup
        userRepository.save(userCreateRequest);

        // when - action or the behaviour that we are going test
        List<User> usersResponse = userRepository.findAll();

        // then - verify the output
        assertThat(usersResponse).isNotNull();
        assertThat(usersResponse.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Test the operation of save user with valid fields")
    public void givenValidUser_whenSave_thenUserIsSaved() {

        // given - precondition or setup

        // when - action or the behaviour that we are going test
        User userResponse = userRepository.save(userCreateRequest);

        // then - verify the output
        assertThat(userResponse).isNotNull();
        assertThat(userResponse.getId()).isEqualTo(userCreateRequest.getId());
        assertThat(userResponse.getUsername()).isEqualTo(userCreateRequest.getUsername());
        assertThat(userResponse.getRoles()).isEqualTo(userCreateRequest.getRoles());
    }
}
