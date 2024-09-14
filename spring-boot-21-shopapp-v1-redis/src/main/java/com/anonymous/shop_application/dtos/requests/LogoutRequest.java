package com.anonymous.shop_application.dtos.requests;


import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LogoutRequest {

    @NotNull(message = "LOGOUT_TOKEN_EMPTY")
    String token;
}
