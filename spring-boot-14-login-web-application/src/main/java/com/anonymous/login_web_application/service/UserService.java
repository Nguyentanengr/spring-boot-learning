package com.anonymous.login_web_application.service;

import com.anonymous.login_web_application.dto.UserRegistrationDto;
import com.anonymous.login_web_application.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    // provide service methods in here

    User save(UserRegistrationDto userRegistrationDto);

}
