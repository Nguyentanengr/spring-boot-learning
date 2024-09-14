package com.anonymous.shop_application.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_details")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    Product product;

    @Column(name = "price")
    Float price;

    @Column(name = "number_of_product")
    Integer numberOfProducts;

    @Column(name = "total_money")
    Float totalMoney;

    @Column(name = "color", length = 20)
    String color;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    @JsonBackReference
    Coupon coupon;
}
