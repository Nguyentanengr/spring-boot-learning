package com.anonymous.login_web_application.exception;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AppException extends RuntimeException {

    ErrorCode errorCode;

    public AppException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    // Create a permission : blank name : -> MethodArgumentNotValidException

    // Delete a permission : invalid ID : -> AppException

    // Delete a permission : constraint foreign key : -> DataIntegrityViolationException


    // Create a role : blank name : -> MethodArgumentNotValidException

    // Create a role : empty permission : -> MethodArgumentNotValidException

    // Create a role : wrong permission : -> AppException

    // Delete a role : invalid ID : -> AppException

    // Delete a role : constraint foreign key : -> DataIntegrityViolationException


    // get a user : invalid ID : -> AppException

    // Create a user : invalidation (firstname, lastname, dob, username, password, roles) : -> MethodArgumentNotValidException

    // Create a user : wrong roles : -> AppException

    // Create a user : existed username : -> AppException

    // Update a user : existed other username : -> AppException

    // Delete a user : invalid ID : -> AppException


}
