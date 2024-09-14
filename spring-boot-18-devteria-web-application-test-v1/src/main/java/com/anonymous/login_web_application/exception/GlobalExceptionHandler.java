package com.anonymous.login_web_application.exception;

import com.anonymous.login_web_application.dto.response.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> handlingValidation(MethodArgumentNotValidException exception) {

        ErrorCode errorCode = ErrorCode.KEY_CODE_INVALID;

        try {
            errorCode = ErrorCode.valueOf(exception.getFieldError().getDefaultMessage());
        } catch (Exception e) {
        }

        return new ResponseEntity<>(
                ApiResponse.builder()
                    .code(errorCode.getCode())
                    .message(errorCode.getMessage())
                    .build(),
                errorCode.getHttpStatusCode()
        );
    }

    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .code(exception.getErrorCode().getCode())
                        .message(exception.getErrorCode().getMessage())
                        .build(),
                exception.getErrorCode().getHttpStatusCode()
        );
    }

    @ExceptionHandler(value = DBException.class)
    public ResponseEntity<ApiResponse> handlingDBException(DBException exception) {
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .code(exception.getErrorCode().getCode())
                        .message(exception.getErrorCode().getMessage())
                        .build(),
                exception.getErrorCode().getHttpStatusCode()
        );
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse> handlingException(Exception exception) {
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .code(ErrorCode.UNCATEGORIZED_EXC.getCode())
                        .message(ErrorCode.UNCATEGORIZED_EXC.getMessage())
                        .build(),
                ErrorCode.UNCATEGORIZED_EXC.getHttpStatusCode()
        );
    }

}
