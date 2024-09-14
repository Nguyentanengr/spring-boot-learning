package com.anonymous.login_web_application.dto.request;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.constraints.*;

import com.anonymous.login_web_application.validator.DobConstraint;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest {

    @NotNull(message = "USER_ID_INVALID")
    Long id;

    @NotBlank(message = "USER_FIRSTNAME_INVALID")
    String firstName;

    @NotBlank(message = "USER_LASTNAME_INVALID")
    String lastName;

    @DobConstraint(min = 18, message = "USER_DOB_INVALID")
    LocalDate dayOfBirth;

    @NotBlank(message = "USER_USERNAME_INVALID_BLANK")
    @Size(min = 3, message = "USER_USERNAME_INVALID_SIZE")
    String username;

    @NotBlank(message = "USER_PASSWORD_INVALID_BLANK")
    @Size(min = 8, message = "USER_PASSWORD_INVALID_SIZE")
    String password;

    @NotEmpty(message = "USER_ROLES_INVALID_EMPTY")
    Set<String> roles;
}
