package com.anonymous.shop_application.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    PENDING,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELLED;
}
