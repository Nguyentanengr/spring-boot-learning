package com.anonymous.shop_application.validators;

import com.anonymous.shop_application.validators.LimitFileImage;
import com.anonymous.shop_application.validators.NotEmptyFile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class NotEmptyFileValidator implements ConstraintValidator<NotEmptyFile, List<MultipartFile>> {


    @Override
    public void initialize(NotEmptyFile constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<MultipartFile> objects, ConstraintValidatorContext constraintValidatorContext) {
        if (objects == null) return false;
        String name = objects.get(0).getOriginalFilename();
        return name != null && !name.isEmpty();
    }
}
