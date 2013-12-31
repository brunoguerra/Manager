package com.ajurasz.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
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


    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private DateTime date;

    @ManyToOne()
    private Customer customer;

    private String documentName;

    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<OrderDetails> orderDetails;

    @ManyToOne
    private Company company;

    public Order() {
        this.date = DateTime.now();
        this.customer = new Customer();
        this.orderDetails = new ArrayList<OrderDetails>();
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
        this.documentName = docNumber.replace("/", "-");
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
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

    public String getDocumentName() {
        return documentName;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
