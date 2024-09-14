package com.anonymous.shop_application.dtos.requests;


import com.anonymous.shop_application.validators.ValidPassword;
import com.anonymous.shop_application.validators.ValidPhoneNumber;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequest {
    @NotNull(message = "USER_PHONE_NUMBER_EMPTY")
    @ValidPhoneNumber(message = "USER_PHONE_NUMBER_INVALID")
    String phoneNumber;

    @NotNull(message = "USER_PASSWORD_EMPTY")
    @ValidPassword(message = "USER_PASSWORD_INVALID")
    String password;
}
