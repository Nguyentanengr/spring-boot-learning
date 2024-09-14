package com.anonymous.shop_application.dtos.responses;

import com.anonymous.shop_application.models.Coupon;
import com.anonymous.shop_application.models.Order;
import com.anonymous.shop_application.models.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailResponse {
    Long id;

    ProductResponse product;

    Float price;

    int numberOfProducts;

    Float totalMoney;

    String color;

    CouponResponse coupon;

}
