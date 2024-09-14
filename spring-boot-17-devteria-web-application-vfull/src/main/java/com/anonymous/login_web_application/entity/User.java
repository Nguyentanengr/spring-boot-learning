package com.anonymous.login_web_application.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "day_of_birth")
    LocalDate dayOfBirth;

    @Column(name = "username", unique = true, columnDefinition = "VARCHAR(255) COLLATE uft8mb4_unicode_ci")
    String username;

    @Column(name = "password")
    String password;

    @ManyToMany
    Set<Role> roles;
}
