package com.ajurasz.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author ajurasz
 */
@Entity
@Table(name = "addresses")
public class Address extends BaseEntity {

    @Column(nullable = false, length = 30)
    @NotEmpty(message = "{customer.city}")
    @Size(max = 30, message = "{customer.city.length}")
    private String city;

    @Column(length = 30)
    @Size(max =30, message = "{customer.street.length}")
    private String street;

    @Column(name = "post_code", nullable = false, length = 6)
    @NotEmpty(message = "{customer.postCode}")
    @Size(max = 6, message = "{customer.postCode.length}")
    @Pattern(regexp = "[0-9]{2}-[0-9]{3}", message = "{customer.postCode.invalid}")
    private String postCode;

    @Column(nullable = false)
    @NotEmpty(message = "{customer.number}")
    private String number;

    @OneToOne(mappedBy = "address")
    @JsonBackReference
    private Customer customer;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getAddress() {
        if(street == null || street.equals("")) {
            return postCode + " " + city + " " + number;
        } else {
            return postCode + " " + city + " ul." + street + " " + number;
        }
    }
}
