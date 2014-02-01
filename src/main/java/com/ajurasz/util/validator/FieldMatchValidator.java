package com.ajurasz.util.validator;

import com.ajurasz.util.annotation.FieldMatch;
import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * source: http://stackoverflow.com/questions/1972933/cross-field-validation-with-hibernate-validator-jsr-303/2155576#2155576
 */
public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String firstFieldName;
    private String secondFieldName;
    private String errorMessage;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        this.firstFieldName = constraintAnnotation.first();
        this.secondFieldName = constraintAnnotation.second();
        this.errorMessage = constraintAnnotation.errorMessage();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        boolean toReturn = false;
        try
        {
            final Object firstObj = BeanUtils.getProperty(value, firstFieldName);
            final Object secondObj = BeanUtils.getProperty(value, secondFieldName);

            toReturn =  firstObj == null && secondObj == null || firstObj != null && firstObj.equals(secondObj);
        }
        catch (final Exception ignore)
        {
            // ignore
        }

        if(!toReturn){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(errorMessage).addNode(secondFieldName).addConstraintViolation();
        }
        return toReturn;
    }
}
