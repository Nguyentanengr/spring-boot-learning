package com.anonymous.shop_application.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {EnumPatternValidator.class}
)
public @interface EnumPattern {

    String message() default "{jakarta.validation.constraints.NotNull.message}";

    String regexp();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
