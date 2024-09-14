package com.anonymous.shop_application.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // User errors
    USER_CREATED(1000, "User has been created", HttpStatus.BAD_REQUEST),
    USER_DELETED(1001, "User has been deleted", HttpStatus.BAD_REQUEST),
    USER_UPDATED(1002, "User has been updated", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1003, "User existed", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1004, "User not found", HttpStatus.NOT_FOUND),
    USERNAME_EXISTED(1005, "Username already exists", HttpStatus.BAD_REQUEST),
    USERNAME_NOT_EXISTED(1006, "Username does not exist", HttpStatus.NOT_FOUND),
    USER_UNAUTHENTICATED(1007, "User unauthenticated", HttpStatus.UNAUTHORIZED),
    USER_PASSWORD_NOT_MATCH(1008, "Retype password not match", HttpStatus.BAD_REQUEST),
    USER_ID_INVALID(1009, "User id invalid", HttpStatus.BAD_REQUEST),

    // User errors
    USER_FULLNAME_INVALID_SIZE(1010, "Full name must not exceed 100 characters", HttpStatus.BAD_REQUEST),
    USER_PHONE_NUMBER_INVALID(1011, "Phone number is invalid", HttpStatus.BAD_REQUEST),
    USER_PHONE_NUMBER_EMPTY(1012, "Phone number is empty", HttpStatus.BAD_REQUEST),
    USER_ADDRESS_INVALID_SIZE(1013, "Address must not exceed 200 characters", HttpStatus.BAD_REQUEST),
    USER_PASSWORD_INVALID(1014, "Password is invalid", HttpStatus.BAD_REQUEST),
    USER_PASSWORD_EMPTY(1015, "Password is empty", HttpStatus.BAD_REQUEST),
    USER_DOB_INVALID(1016, "User must be at least 16 years old", HttpStatus.BAD_REQUEST),
    USER_ROLE_INVALID_EMPTY(1017, "User must have at least one role", HttpStatus.BAD_REQUEST),
    USER_ACTIVE_INVALID(1018, "Active only 0 or 1", HttpStatus.BAD_REQUEST),
    USER_ID_NOT_NULL(1019, "User id must not be empty", HttpStatus.BAD_REQUEST),

    // Role errors
    ROLE_NAME_INVALID(1020, "Name of role must not be blank", HttpStatus.BAD_REQUEST),
    ROLE_PERMISSIONS_INVALID_EMPTY(1021, "Permissions of role must not be empty", HttpStatus.BAD_REQUEST),
    ROLE_NOT_FOUND(1025, "Role not found", HttpStatus.NOT_FOUND),
    ROLE_EXISTED(1026, "Role already exists", HttpStatus.BAD_REQUEST),
    ROLE_FOREIGN_KEY_VIOLATED(1027, "This role is associated with several users", HttpStatus.BAD_REQUEST),

    // Permission errors
    PERMISSION_NAME_INVALID(1030, "Name of permission must not be blank", HttpStatus.BAD_REQUEST),
    PERMISSION_NOT_FOUND(1031, "Permission not found", HttpStatus.BAD_REQUEST),
    PERMISSION_EXISTED(1032, "Permission already exists", HttpStatus.BAD_REQUEST),
    PERMISSION_FOREIGN_KEY_VIOLATED(1033, "This permission is associated with several roles", HttpStatus.BAD_REQUEST),

    // Category errors
    CATEGORY_NOT_FOUND(1040, "Category not found", HttpStatus.NOT_FOUND),
    CATEGORY_NAME_EXISTED(1041, "Category name already exists", HttpStatus.BAD_REQUEST),
    CATEGORY_NOT_DELETE(1042, "Cannot delete category with associated products", HttpStatus.BAD_REQUEST),
    CATEGORY_NAME_INVALID_BLANK(1043, "Category name must not be blank", HttpStatus.BAD_REQUEST),
    CATEGORY_NAME_INVALID_SIZE(1044, "Category name must be between 3 and 200 characters", HttpStatus.BAD_REQUEST),

    // Product errors
    PRODUCT_NAME_INVALID_BLANK(1050, "Product name must not be blank", HttpStatus.BAD_REQUEST),
    PRODUCT_NAME_INVALID_SIZE(1051, "Product name must be between 3 and 200 characters", HttpStatus.BAD_REQUEST),
    PRODUCT_PRICE_INVALID_AMOUNT(1053, "Price must be between 0 and 10,000,000", HttpStatus.BAD_REQUEST),
    PRODUCT_NOT_FOUND(1054, "Product not found", HttpStatus.NOT_FOUND),
    PRODUCT_CATEGORY_ID_INVALID(1055, "Category id invalid", HttpStatus.BAD_REQUEST),
    PRODUCT_ID_INVALID(1056, "Product id invalid", HttpStatus.BAD_REQUEST),
    PRODUCT_ID_NOT_NULL(1057, "Product id must not be empty", HttpStatus.BAD_REQUEST),

    // Product image errors
    PRODUCT_IMAGE_FILE_INVALID_EMPTY(1060, "File image must not be empty", HttpStatus.BAD_REQUEST),
    PRODUCT_IMAGE_FILE_INVALID_SIZE(1061, "Cannot upload more than 5 images per product", HttpStatus.BAD_REQUEST),
    PRODUCT_IMAGE_FILE_INVALID_QUALIFIED(1062, "File image must be a valid content type and no larger than 10MB", HttpStatus.BAD_REQUEST),
    PRODUCT_IMAGE_PRODUCT_ID_INVALID(1063, "Product id invalid", HttpStatus.BAD_REQUEST),
    PRODUCT_IMAGE_ID_INVALID(1064, "Image id invalid", HttpStatus.BAD_REQUEST),
    PRODUCT_IMAGE_ID_INVALID_EMPTY(1065, "Image id must not be empty", HttpStatus.BAD_REQUEST),
    PRODUCT_IMAGE_NOT_FOUND(1065, "One or more image IDs were not found in the product", HttpStatus.BAD_REQUEST),
    PRODUCT_IMAGE_ERROR_DELETE(1066, "An error occurred while trying to delete the original file", HttpStatus.BAD_REQUEST),

    // Order errors
    ORDER_ID_INVALID(1070, "Order id invalid", HttpStatus.BAD_REQUEST),
    ORDER_DETAIL_ID_EMPTY(1071, "Must be at least one order detail", HttpStatus.BAD_REQUEST),
    ORDER_ADDRESS_NULL(1072, "Address must not be empty", HttpStatus.BAD_REQUEST),
    ORDER_NOT_FOUND(1073, "Order not found", HttpStatus.NOT_FOUND),
    ORDER_STATUS_INVALID(1078, "Order status invalid", HttpStatus.BAD_REQUEST),
    ORDER_STATUS_EMPTY(1079, "Order status must be not emtpy", HttpStatus.BAD_REQUEST),

    // Order detail error
    ORDER_DETAIL_NUMBER_PRODUCT_INVALID(1080, "Number of product invalid", HttpStatus.BAD_REQUEST),
    ORDER_DETAIL_TOTAL_MONEY_INVALID(1081, "Total money invalid", HttpStatus.BAD_REQUEST),
    ORDER_DETAIL_NOT_FOUND(1082, "Order detail not found", HttpStatus.BAD_REQUEST),

    // Coupon error
    COUPON_CODE_INVALID_EMPTY(1090, "Coupon code must not be empty", HttpStatus.BAD_REQUEST),
    COUPON_CODE_INVALID_SIZE(1091, "Coupon code invalid size", HttpStatus.BAD_REQUEST),
    COUPON_START_DATE_INVALID(1092, "Coupon start date must be present or future day", HttpStatus.BAD_REQUEST),
    COUPON_END_DATE_INVALID(1093, "Coupon end date must be future day", HttpStatus.BAD_REQUEST),
    COUPON_DISCOUNT_PERCENT_INVALID(1094, "Coupon discount percent must be between 0 and 100", HttpStatus.BAD_REQUEST),
    COUPON_DISCOUNT_AMOUNT_INVALID(1095, "Coupon discount amount must be greater than 0", HttpStatus.BAD_REQUEST),
    COUPON_ACTIVE_INVALID(1096, "Coupon active only 0 or 1", HttpStatus.BAD_REQUEST),
    COUPON_NOT_FOUND(1097, "Coupon not found", HttpStatus.BAD_REQUEST),
    COUPON_CODE_EXISTS(1098, "Coupon code already exists", HttpStatus.BAD_REQUEST),
    COUPON_DATE_INVALID(1099, "Coupon end date must be at least 1 hour after the start date", HttpStatus.BAD_REQUEST),
    COUPON_DISCOUNT_INVALID(1100, "Coupon discount amount or percentage not both", HttpStatus.BAD_REQUEST),
    COUPON_ID_INVALID(1011, "Coupon id invalid", HttpStatus.BAD_REQUEST),
    COUPON_EXPIRED(1012, "Coupon with id: %s is not yet active or expired", HttpStatus.BAD_REQUEST),

    // Logout error
    LOGOUT_TOKEN_EMPTY(1020, "Token must not be empty", HttpStatus.BAD_REQUEST),

    // General errors
    JSON_PARSE_EXC(9988, "Json can not parse to object", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXC(9990, "Uncategorized error", HttpStatus.BAD_REQUEST),
    KEY_CODE_INVALID(9999, "Error message field does not match with ErrorCode enum", HttpStatus.NOT_FOUND);

    private final long code;
    private final String message;
    private final HttpStatus httpStatus;

    public String getMessage(String... args) {
        if (args != null && args.length > 0) {
            return String.format(message, (Object[]) args);
        }
        return message;
    }
}
