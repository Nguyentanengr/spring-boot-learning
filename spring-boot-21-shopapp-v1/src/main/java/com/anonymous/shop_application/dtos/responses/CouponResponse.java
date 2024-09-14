package com.anonymous.shop_application.dtos.responses;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CouponResponse {
    Long id;

    String code;

    int isActive;

    LocalDateTime startDate;

    LocalDateTime endDate;

    Float discountPercentage;

    Float discountAmount;

}
