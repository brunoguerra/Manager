package com.ajurasz.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Arek Jurasz
 */
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    @Column(name = "document_number", nullable = false)
    @Pattern(regexp = "([0-9]{1,5})/([0-9]{2})/([0-9]{4})", message = "{order.docNumber.format}")
    private String docNumber;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date date;

    @ManyToOne()
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetails;

    public Order() {
        this.date = new Date();
        this.customer = new Customer();
        this.orderDetails = new ArrayList<OrderDetails>();
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
