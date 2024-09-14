package com.anonymous.shop_application.dtos.requests;

import com.anonymous.shop_application.models.Role;
import com.anonymous.shop_application.validators.DobConstraint;
import com.anonymous.shop_application.validators.ValidPassword;
import com.anonymous.shop_application.validators.ValidPhoneNumber;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {

    @Size(max = 100, message = "USER_FULLNAME_INVALID_SIZE")
    String fullName;

    @NotNull(message = "USER_PHONE_NUMBER_EMPTY")
    @ValidPhoneNumber(message = "USER_PHONE_NUMBER_INVALID")
    String phoneNumber;

    @Size(max = 200, message = "USER_ADDRESS_INVALID_SIZE")
    String address;

    @NotNull(message = "USER_PASSWORD_EMPTY")
    @ValidPassword(message = "USER_PASSWORD_INVALID")
    String password;

    @NotNull(message = "USER_PASSWORD_EMPTY")
    @ValidPassword(message = "USER_PASSWORD_INVALID")
    String retypePassword;

    @Min(value = 0, message = "USER_ACTIVE_INVALID")
    @Max(value = 1, message = "USER_ACTIVE_INVALID")
    int isActive;

    @DobConstraint(min = 16, message = "USER_DOB_INVALID") // it nhat 16 tuoi
    LocalDate dayOfBirth;

    Long facebookAccountId;

    Long googleAccountId;

    @NotEmpty(message = "USER_ROLE_INVALID_EMPTY") // it nhat phai co role
    Set<Long> roles;
}
