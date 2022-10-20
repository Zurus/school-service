package ru.secure_environment.arm.util.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = HasSchoolValidator.class)
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface HasSchool {
    String message() default "{error.schoolNotFound}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
