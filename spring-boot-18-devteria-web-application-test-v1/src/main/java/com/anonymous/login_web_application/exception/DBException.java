package com.anonymous.login_web_application.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.dao.DataIntegrityViolationException;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DBException extends DataIntegrityViolationException {

    ErrorCode errorCode;

    public DBException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
