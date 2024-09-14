package com.anonymous.login_web_application.dto.request;

import com.anonymous.login_web_application.entity.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {

    @NotBlank(message = "USER_FIRSTNAME_INVALID")
    String firstName;

    @NotBlank(message = "USER_LASTNAME_INVALID")
    String lastName;

    @NotNull(message = "USER_DAYOFBIRTH_INVALID")
    LocalDate dayOfBirth;

    @NotBlank(message = "USERNAME_INVALID")
    @Size(min = 3, message = "USERNAME_INVALID")
    String username;

    @NotBlank(message = "PASSWORD_INVALID")
    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;

    Set<String> roles;
}
