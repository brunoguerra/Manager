package com.ajurasz.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.core.style.ToStringCreator;

import javax.persistence.*;
import javax.validation.Valid;

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

    @Email
    private String email;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    @Valid
    private Address address;

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
}
