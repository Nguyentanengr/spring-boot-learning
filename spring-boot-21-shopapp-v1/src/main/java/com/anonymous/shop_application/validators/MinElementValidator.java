package com.anonymous.shop_application.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class MinElementValidator implements ConstraintValidator<MinElement, List<Long>> {

    private int min;


    @Override
    public void initialize(MinElement constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(List<Long> ids, ConstraintValidatorContext constraintValidatorContext) {
        for (Long id : ids) {
            if (id < min) return false;
        }
        return true;
    }
}
