package com.anonymous.login_web_application.dto.request;


import com.anonymous.login_web_application.entity.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleRequest {

    @NotBlank
    String name;

    String description;

    Set<String> permissions;
}
