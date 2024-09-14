package com.anonymous.shop_application.dtos.responses;

import com.anonymous.shop_application.models.Status;
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
public class OrderResponse {

    Long id;

    UserResponse user;

    String fullName;

    String email;

    String phoneNumber;

    String address;

    String note;

    LocalDateTime orderDate;

    Status status;

    Float totalMoney;

    String shippingMethod;

    String shippingAddress;

    LocalDate shippingDate;

    String trackingNumber;

    String paymentMethod;

    int active;

    Set<OrderDetailResponse> orderDetails;

    CouponResponse coupon;

}
