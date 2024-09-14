package com.anonymous.login_web_application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    USER_CREATED(1000, "User has been created", HttpStatus.BAD_REQUEST),
    USER_DELETED(1000, "User has been deleted", HttpStatus.BAD_REQUEST),
    USER_UPDATED(1000, "User has been update", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1001, "User existed", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1002, "User not found", HttpStatus.NOT_FOUND),
    USERNAME_EXISTED(1005, "Username already exists", HttpStatus.BAD_REQUEST),
    USERNAME_NOT_EXISTED(1006, "Username not exists", HttpStatus.NOT_FOUND),
    USER_UNAUTHENTICATED(1007, "User unauthenticated", HttpStatus.UNAUTHORIZED),

    USER_FIRSTNAME_INVALID(1010, "Firstname must not be blank", HttpStatus.BAD_REQUEST),
    USER_LASTNAME_INVALID(1011, "Lastname must not be blank", HttpStatus.BAD_REQUEST),
    USER_DOB_INVALID(1012, "User must be over 18 years old", HttpStatus.BAD_REQUEST),
    USER_USERNAME_INVALID_BLANK(1013, "Username must not be blank", HttpStatus.BAD_REQUEST),
    USER_USERNAME_INVALID_SIZE(1014, "Username must be at least 3 characters", HttpStatus.BAD_REQUEST),
    USER_PASSWORD_INVALID_BLANK(1015, "Password must not be blank", HttpStatus.BAD_REQUEST),
    USER_PASSWORD_INVALID_SIZE(1016, "Password must be at least 8 characters", HttpStatus.BAD_REQUEST),
    USER_ROLES_INVALID_EMPTY(1017, "Roles of user must not be empty", HttpStatus.BAD_REQUEST),
    USER_ID_INVALID(1018, "User id must be greater than 0", HttpStatus.BAD_REQUEST),

    ROLE_NAME_INVALID(1020, "Name of role must not be blank", HttpStatus.BAD_REQUEST),
    ROLE_PERMISSIONS_INVALID_EMPTY(1021, "Permissions of role must not be empty", HttpStatus.BAD_REQUEST),
    ROLE_NOT_FOUND(1025, "Role not found", HttpStatus.NOT_FOUND),
    ROLE_EXISTED(1026, "Role already exists", HttpStatus.BAD_REQUEST),
    ROLE_FOREIGN_KEY_VIOLATED(1027, "This role is associated with several users", HttpStatus.BAD_REQUEST),

    PERMISSION_NAME_INVALID(1030, "Name of permission must not be blank", HttpStatus.BAD_REQUEST),
    PERMISSION_NOT_FOUND(1031, "Permission not found", HttpStatus.BAD_REQUEST),
    PERMISSION_EXISTED(1032, "Permission already exists", HttpStatus.BAD_REQUEST),
    PERMISSION_FOREIGN_KEY_VIOLATED(1033, "This permission is associated with several roles", HttpStatus.BAD_REQUEST),

    UNCATEGORIZED_EXC(9990, "Uncategorized error", HttpStatus.BAD_REQUEST),
    KEY_CODE_INVALID(9999, "Error message field not match with ErrorCode enum", HttpStatus.NOT_FOUND),
    ;

    private long code;

    private String message;

    private HttpStatusCode httpStatusCode;
}
