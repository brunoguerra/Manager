package com.ajurasz.util.annotation;

import com.ajurasz.util.validator.UniqueEmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Arek Jurasz
 */
@Target( {FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = UniqueEmailValidator.class)
public @interface UniqueEmail {

    String message() default "This E-mail all ready exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
