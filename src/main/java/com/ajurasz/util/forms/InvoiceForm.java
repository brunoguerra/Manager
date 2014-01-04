package com.ajurasz.util.forms;

import com.ajurasz.model.Order;

/**
 * @author Arek Jurasz
 */
public class InvoiceForm {
    public boolean isExcise() {
        return excise;
    }

    public void setExcise(boolean excise) {
        this.excise = excise;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public static enum Payment {
        CASH, TRANSFER, INSTALLMENT;
    }
    private boolean excise;
    private Payment payment;
    private Order order;

    public InvoiceForm() {
        order = new Order();
        payment = Payment.CASH;
        excise = false;
    }
}
