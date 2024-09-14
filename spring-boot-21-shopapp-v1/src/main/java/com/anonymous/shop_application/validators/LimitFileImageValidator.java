package com.anonymous.shop_application.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.constraints.Max;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class LimitFileImageValidator implements ConstraintValidator<LimitFileImage, List<MultipartFile>> {

    private int max;

    @Override
    public void initialize(LimitFileImage constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(List<MultipartFile> objects, ConstraintValidatorContext constraintValidatorContext) {
        String name = objects.get(0).getOriginalFilename();
        return name != null && !name.isEmpty() && objects.size() <= max;
    }
}
