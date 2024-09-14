package com.anonymous.shop_application.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ValidImageValidator implements ConstraintValidator<ValidImage, List<MultipartFile>> {

    private int size;

    private String[] contentTypes;

    @Override
    public void initialize(ValidImage constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.size = constraintAnnotation.size();
        this.contentTypes = constraintAnnotation.contentTypes();
    }

    @Override
    public boolean isValid(List<MultipartFile> files, ConstraintValidatorContext constraintValidatorContext) {

        for (MultipartFile file : files) {
            if (file.getSize() == 0 || file.getSize() > size * 1024L) {
                return false;
            }

            if (contentTypes.length > 0) {
                boolean isValid = false;
                for (String type : contentTypes) {
                    if (file.getContentType().startsWith(type)) {
                        isValid = true; break;
                    }
                }
                if (!isValid) return false;
            }
        }

        return true;
    }
}
