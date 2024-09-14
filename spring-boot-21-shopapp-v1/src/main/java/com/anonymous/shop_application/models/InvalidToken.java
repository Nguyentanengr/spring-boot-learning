package com.anonymous.shop_application.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invalid_tokens")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvalidToken extends BaseEntity{

    @Id
    String id;

    @Column(name = "expiration_time", nullable = false)
    Date expirationTime;
}
