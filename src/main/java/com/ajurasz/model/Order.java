package com.ajurasz.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
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

    @Column(name = "document_number")
    @Pattern(regexp = "([0-9]{1,5})/([0-9]{2})/([0-9]{4})", message = "{order.docNumber.format}")
    private String docNumber;

    @Column(name = "invoice_number")
    private String invoiceNumber;


    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "order_date")
    private DateTime orderDate;

    @ManyToOne()
    private Customer customer;

    @Column(name = "document_name")
    private String documentName;

    @Column(name = "document_invoice_name")
    private String documentInvoiceName;

    private Boolean invoice;
    private Boolean document;

    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<OrderDetails> orderDetails;

    @ManyToOne
    private Company company;

    public Order() {
//        this.orderDate = DateTime.now();
        this.invoice = false;
        this.document = true;
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

    public DateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(DateTime orderDate) {
        if(orderDate == null) {
            this.orderDate = orderDate;
            return;
        }
        this.orderDate = new DateTime(
                orderDate.get(DateTimeFieldType.year()),
                orderDate.get(DateTimeFieldType.monthOfYear()),
                orderDate.get(DateTimeFieldType.dayOfMonth()),
                0, 0, 0, 0
        );
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

    public boolean isInvoice() {
        return invoice;
    }

    public void setInvoice(boolean invoice) {
        this.invoice = invoice;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
        this.documentInvoiceName = invoiceNumber.replace("/", "-");
    }

    public String getDocumentInvoiceName() {
        return documentInvoiceName;
    }

    public boolean isDocument() {
        return document;
    }

    public void setDocument(boolean document) {
        this.document = document;
    }
}
