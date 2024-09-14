package com.anonymous.shop_application.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(name = "full_name", length = 200)
    String fullName;

    @Column(name = "email", length = 200)
    String email;

    @Column(name = "phone_number", nullable = false, length = 10)
    String phoneNumber;

    @Column(name = "address", nullable = false, length = 200)
    String address;

    @Column(name = "note", length = 200)
    String note;

    @Column(name = "order_date", nullable = false)
    LocalDateTime orderDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    Status status;

    @Column(name = "total_money")
    Float totalMoney;

    @Column(name = "shipping_method", length = 100)
    String shippingMethod;

    @Column(name = "shipping_address", length = 200)
    String shippingAddress;

    @Column(name = "shipping_date")
    LocalDate shippingDate;

    @Column(name = "tracking_number", length = 100)
    String trackingNumber;

    @Column(name = "payment_method", length = 100)
    String paymentMethod;

    @Column(name = "active")
    int active;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonBackReference
    Set<OrderDetail> orderDetails;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    @JsonBackReference
    Coupon coupon;
}
