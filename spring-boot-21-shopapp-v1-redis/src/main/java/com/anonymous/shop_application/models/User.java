package com.anonymous.shop_application.models;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "phone_number")
})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "full_name", length = 100)
    String fullName;

    @Column(name = "phone_number", nullable = false, length = 10)
    String phoneNumber;

    @Column(name = "address", length = 200)
    String address;

    @Column(name = "password", nullable = false, length = 100)
    String password;

    @Column(name = "is_active")
    @Builder.Default
    int isActive = 1;

    @Column(name = "day_of_birth")
    LocalDate dayOfBirth;

    @Column(name = "facebook_account_id")
    Long facebookAccountId;

    @Column(name = "google_account_id")
    Long googleAccountId;

    @ManyToMany
    Set<Role> roles;

}
