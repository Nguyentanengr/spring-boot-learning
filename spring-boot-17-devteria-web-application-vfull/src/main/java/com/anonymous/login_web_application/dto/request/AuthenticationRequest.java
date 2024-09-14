package com.anonymous.login_web_application.dto.request;

import com.anonymous.login_web_application.exception.ErrorCode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequest {

    @NotBlank(message = "USER_USERNAME_INVALID_BLANK")
    @Size(min = 3, message = "USER_USERNAME_INVALID_SIZE")
    String username;

    @NotBlank(message = "USER_PASSWORD_INVALID_BLANK")
    @Size(min = 8, message = "USER_PASSWORD_INVALID_SIZE")
    String password;

}
