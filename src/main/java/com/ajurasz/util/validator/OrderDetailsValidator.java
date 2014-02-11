package com.ajurasz.util.validator;


import com.ajurasz.model.OrderDetails;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

/**
 * @author Arek Jurasz
 */
public class OrderDetailsValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return OrderDetails.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        List<OrderDetails> orderDetails = (List<OrderDetails>) target;
        int counter = 0;
        for(OrderDetails od : orderDetails) {
            if(od.getItem().getId() == null)
                errors.rejectValue("orderDetails[" + counter + "].item", "order.validation.orderDetails.item-required");
            if(od.getReason().getId() == null)
                errors.rejectValue("orderDetails[" + counter + "].reason", "order.validation.orderDetails.reason-required");
            counter++;
        }
    }
}
