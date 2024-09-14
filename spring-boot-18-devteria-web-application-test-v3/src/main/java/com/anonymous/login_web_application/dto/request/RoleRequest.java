package com.anonymous.login_web_application.dto.request;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleRequest {

    @NotBlank(message = "ROLE_NAME_INVALID")
    String name;

    String description;

    @NotEmpty(message = "ROLE_PERMISSIONS_INVALID_EMPTY")
    Set<String> Permissions;
}
