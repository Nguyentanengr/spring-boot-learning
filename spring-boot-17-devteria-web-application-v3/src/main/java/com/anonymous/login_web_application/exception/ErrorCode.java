package com.anonymous.login_web_application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    USER_CREATED(1000, "User has been created", HttpStatus.BAD_REQUEST),
    USER_DELETED(1000, "User has been deleted", HttpStatus.BAD_REQUEST),
    USER_UPDATED(1000, "User has been update", HttpStatus.BAD_REQUEST),

    USER_EXISTED(1001, "User existed", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1002, "User not found", HttpStatus.NOT_FOUND),
    USERNAME_EXSITED(1005, "Username already exists", HttpStatus.BAD_REQUEST),
    USERNAME_NOT_EXSITED(1006, "Username not exists", HttpStatus.NOT_FOUND),

    USER_FIRSTNAME_INVALID(1020, "First name is mandatory", HttpStatus.BAD_REQUEST),
    USER_LASTNAME_INVALID(1021, "Last name is mandatory", HttpStatus.BAD_REQUEST),
    USER_DAYOFBIRTH_ISNULL(1022, "Day of birth is mandatory", HttpStatus.BAD_REQUEST),
    USER_DAYOFBIRTH_INVALID(1022, "Invalid day of birth", HttpStatus.BAD_REQUEST),


    USERNAME_INVALID(1024, "Username must be at least 3 characters or not blank", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1025, "Password must be at least 8 characters or not blank", HttpStatus.BAD_REQUEST),

    PERMISSION_NAME_INVALID(1027, "Permission's name is mandatory", HttpStatus.BAD_REQUEST),
    PERMISSION_NOT_FOUND(1028, "Permission not found", HttpStatus.NOT_FOUND),

    ROLE_NAME_INVALID(1029, "Permission's name is mandatory", HttpStatus.BAD_REQUEST),
    ROLE_NOT_FOUND(1030, "Role not found", HttpStatus.NOT_FOUND),

    KEY_CODE_INVALID(1030, "Message in validation not match with error code",HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXC(1040, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),

    USER_UNAUTHENTICATED(1050, "unauthenticated user", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1051, "You do not have permission user", HttpStatus.FORBIDDEN)
    ;

    private int code;

    private String message;

    private HttpStatusCode statusCode;

}
