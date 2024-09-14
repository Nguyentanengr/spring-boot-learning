package com.anonymous.login_web_application.dto.response;

import com.anonymous.login_web_application.entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    Long id;
    String firstName;
    String lastName;
    LocalDate dayOfBirth;
    String username;
    Set<RoleResponse> roles;
}