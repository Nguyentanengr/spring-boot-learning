package com.anonymous.shop_application.dtos.requests;

import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CouponRequest {

    @NotEmpty(message = "COUPON_CODE_INVALID_EMPTY") // khong null va khong rong
    @Size(min = 10, max = 10, message = "COUPON_CODE_INVALID_SIZE")
    String code;

    @FutureOrPresent(message = "COUPON_START_DATE_INVALID")
    LocalDateTime startDate;

    @Future(message = "COUPON_END_DATE_INVALID")
    LocalDateTime endDate;

    @Min(value = 0, message = "COUPON_DISCOUNT_PERCENT_INVALID")
    @Max(value = 100, message = "COUPON_DISCOUNT_PERCENT_INVALID")
    Float discountPercentage;

    @Min(value = 0, message = "COUPON_DISCOUNT_AMOUNT_INVALID")
    Float discountAmount;
}
