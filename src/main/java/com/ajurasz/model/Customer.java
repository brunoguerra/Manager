package com.ajurasz.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.core.style.ToStringCreator;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

//todo:intergrate with hibernate-search
/**
 * @author Arek Jurasz
 */
@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {

    @Column(name = "first_name", nullable = false)
    @NotEmpty(message = "{customer.firstName}")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotEmpty(message = "{customer.lastName}")
    private String lastName;

    @Email(message = "{customer.email}")
    private String email;

    //@Digits(integer = 9, fraction = 0, message = "{customer.pesel}")
    private String pesel;

    @Digits(integer = 10, fraction = 0, message = "{customer.phoneNumber}")
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Valid
    @NotNull
    @JsonManagedReference
    private Address address;

    @ManyToOne
    private Company company;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
        this.address.setCustomer(this);
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)

                .append("id", this.getId())
                .append("lastName", this.getLastName())
                .append("firstName", this.getFirstName())
                .append("email", this.getEmail())
                .toString();
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
