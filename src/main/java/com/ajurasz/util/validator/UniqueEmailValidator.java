package com.ajurasz.util.validator;

import com.ajurasz.model.Company;
import com.ajurasz.service.ManagerService;
import com.ajurasz.util.annotation.UniqueEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Arek Jurasz
 */
@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ManagerService managerService;

    private UniqueEmail uniqueEmail;

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        this.uniqueEmail = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            entityManager.setFlushMode(FlushModeType.COMMIT);
            Company company = managerService.findCompanyByUsername(value);
            if(company == null)
                return true;
            else
                return false;

        } finally {
            entityManager.setFlushMode(FlushModeType.AUTO);
        }
    }
}
