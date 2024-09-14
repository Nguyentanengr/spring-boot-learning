package com.anonymous.shop_application.dtos.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {

    Long id;

    String fullName;

    String phoneNumber;

    String address;

    int isActive;

    LocalDate dayOfBirth;

    LocalDateTime createdAt;

    LocalDateTime updatedAt;

    Set<RoleResponse> roles;
}
