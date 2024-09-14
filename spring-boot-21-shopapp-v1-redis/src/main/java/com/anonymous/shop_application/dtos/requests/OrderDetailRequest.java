package com.anonymous.shop_application.dtos.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailRequest {

    @NotNull(message = "PRODUCT_ID_NOT_NULL")
    @Min(value = 1, message = "PRODUCT_ID_INVALID")
    Long productId;

    @NotNull
    @Min(value = 0, message = "PRODUCT_PRICE_INVALID_AMOUNT")
    @Max(value = 10000000, message = "PRODUCT_PRICE_INVALID_AMOUNT")
    Float price;

    @Min(value = 1, message = "ORDER_DETAIL_NUMBER_PRODUCT_INVALID")
    Integer numberOfProducts;

    @Min(value = 0, message = "ORDER_DETAIL_TOTAL_MONEY_INVALID")
    Float totalMoney;

    String color;

    @Min(value = 1, message = "COUPON_ID_INVALID")
    Long couponId;

}
