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

    @NotEmpty(message = "{address.validation.city-required}")
    @Column(nullable = false)
    private String city;

    private String street;


    @NotEmpty(message = "{address.validation.post-code-required}")
    @Size(max = 6, message = "{address.validation.post-code-length}")
    @Pattern(regexp = "[0-9]{2}-[0-9]{3}", message = "{address.validation.post-code-pattern}")
    @Column(name = "post_code", nullable = false, length = 6)
    private String postCode;


    @NotEmpty(message = "{address.validation.number-required}")
    @Column(nullable = false)
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
        if(street == null || street.equals("") || street.equals(" ")) {
            return postCode + " " + city + " " + number;
        } else {
            return postCode + " " + city + " ul." + street + " " + number;
        }
    }

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", postCode='" + postCode + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
