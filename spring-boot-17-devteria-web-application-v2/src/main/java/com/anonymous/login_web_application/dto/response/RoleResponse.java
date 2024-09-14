package com.anonymous.login_web_application.dto.response;


import com.anonymous.login_web_application.entity.Permission;
import com.anonymous.login_web_application.entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleResponse {

    String name;

    String description;

    Set<PermissionResponse> permissions;

}
