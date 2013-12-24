package com.ajurasz.util.annotation;

import com.ajurasz.util.validator.UniqueNameValidator;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author Arek Jurasz
 */
@Target( {FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueNameValidator.class)
public @interface UniqueName {
    String message() default "This name all ready exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
