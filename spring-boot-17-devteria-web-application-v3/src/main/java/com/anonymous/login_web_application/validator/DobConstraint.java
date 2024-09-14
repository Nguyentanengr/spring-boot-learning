package com.anonymous.login_web_application.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(
        validatedBy = {DobValidator.class}
)
public @interface DobConstraint {

    String message() default "{jakarta.validation.constraints.NotNull.message}";

    int min();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
