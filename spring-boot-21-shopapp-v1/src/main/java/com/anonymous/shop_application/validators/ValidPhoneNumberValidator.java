package com.anonymous.shop_application.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ValidPhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {


    @Override
    public void initialize(ValidPhoneNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        if (phoneNumber == null) return true;
        return phoneNumber.length() == 10 && phoneNumber.startsWith("0");
    }

}
