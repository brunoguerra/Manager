package com.ajurasz.util.forms;

import com.ajurasz.model.Order;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author Arek Jurasz
 */
public class InvoiceForm {

    private boolean excise;
    private boolean grossInvoice;
    private Payment payment;
    private Order order;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private DateTime paymentDate;

    public InvoiceForm() {
        order = new Order();
        payment = Payment.CASH;
        excise = true;
        grossInvoice = false;
    }

    public DateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(DateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public boolean isGrossInvoice() {
        return grossInvoice;
    }

    public void setGrossInvoice(boolean grossInvoice) {
        this.grossInvoice = grossInvoice;
    }

    public static enum Payment {
        CASH("gotówka"), TRANSFER("przelew"), INSTALLMENT("płatność ratalna");

        private String description;
        private Payment(String desc){
            this.description = desc;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

    }

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



}
