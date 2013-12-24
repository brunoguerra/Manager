package com.ajurasz.util.validator;

import com.ajurasz.model.Item;
import com.ajurasz.repository.ItemRepository;
import com.ajurasz.util.annotation.UniqueName;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Arek Jurasz
 */
public class UniqueNameValidator implements ConstraintValidator<UniqueName, String> {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ItemRepository itemRepo;

    private UniqueName uniqueName;

    @Override
    public void initialize(UniqueName constraintAnnotation) {
        this.uniqueName = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            entityManager.setFlushMode(FlushModeType.COMMIT);
            Item item = itemRepo.findItemByName(value);
            if(item == null)
                return true;
            else
                return false;

        } finally {
            entityManager.setFlushMode(FlushModeType.AUTO);
        }
    }


}
