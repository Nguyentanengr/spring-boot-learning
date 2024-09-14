package com.anonymous.login_web_application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anonymous.login_web_application.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {}
