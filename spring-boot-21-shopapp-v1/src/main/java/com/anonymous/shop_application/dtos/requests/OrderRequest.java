package com.anonymous.shop_application.dtos.requests;

import com.anonymous.shop_application.models.Status;
import com.anonymous.shop_application.validators.EnumPattern;
import com.anonymous.shop_application.validators.ValidPhoneNumber;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {

    @NotNull(message = "USER_ID_NOT_NULL")
    @Min(value = 1, message = "USER_ID_INVALID")
    Long userId;

    @Builder.Default
    String fullName = "";

    @Builder.Default
    String email = "";

    @NotNull(message = "USER_PHONE_NUMBER_EMPTY")
    @ValidPhoneNumber(message = "USER_PHONE_NUMBER_INVALID")
    String phoneNumber;

    @NotNull(message = "ORDER_ADDRESS_NULL")
    @Size(max = 200, message = "USER_ADDRESS_INVALID_SIZE")
    String address;

    @Builder.Default
    String note = "";

    @NotNull(message = "ORDER_STATUS_EMPTY")
    @EnumPattern(regexp = "^PENDING|PROCESSING|SHIPPED|DELIVERED|CANCELLED$", message = "ORDER_STATUS_INVALID")
    Status status;

    @Builder.Default
    String shippingMethod = "";

    @Builder.Default
    String shippingAddress = "";

    @Builder.Default
    String paymentMethod = "";

    @NotEmpty(message = "ORDER_DETAIL_ID_EMPTY")
    Set<@Valid OrderDetailRequest> orderDetails;

    @Min(value = 1, message = "COUPON_ID_INVALID")
    Long couponId;
}
