package com.anonymous.login_web_application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USER_CREATED(1000, "User has been created"),
    USER_DELETED(1000, "User has been deleted"),
    USER_UPDATED(1000, "User has been update"),

    USER_EXISTED(1001, "User existed"),
    USER_NOT_FOUND(1002, "User not found"),
    USERNAME_EXSITED(1005, "Username already exists"),
    USERNAME_NOT_EXSITED(1006, "Username not exists"),

    USER_FIRSTNAME_INVALID(1020, "First name is mandatory"),
    USER_LASTNAME_INVALID(1021, "Last name is mandatory"),
    USER_DAYOFBIRTH_INVALID(1022, "Day of birth is mandatory"),

    USERNAME_INVALID(1024, "Username must be at least 3 characters or not blank"),
    PASSWORD_INVALID(1025, "Password must be at least 8 characters or not blank"),

    KEY_CODE_INVALID(1030, "Message in validation not match with error code"),
    UNCATEGORIZED_EXC(1040, "Uncategorized error"),

    USER_UNAUTHENTICATED(1050, "unauthenticated user")
    ;

    private int code;

    private String message;

}
