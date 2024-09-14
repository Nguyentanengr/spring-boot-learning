package com.anonymous.shop_application.exceptions;

import com.anonymous.shop_application.dtos.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse<?>> handlingAppException(AppException exception) {
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .code(exception.getErrorCode().getCode())
                        .message(exception.getMessage())
                        .build(),
                exception.getErrorCode().getHttpStatus()
        );
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handlingValidation(MethodArgumentNotValidException exception) {
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
                errorCode.getHttpStatus()
        );
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<?>> handlingJsonParseException(HttpMessageNotReadableException exception) {
        return new ResponseEntity<>(
                ApiResponse.builder()
                        .code(ErrorCode.JSON_PARSE_EXC.getCode())
                        .message(ErrorCode.JSON_PARSE_EXC.getMessage())
                        .build(),
                ErrorCode.JSON_PARSE_EXC.getHttpStatus()
        );
    }
}
