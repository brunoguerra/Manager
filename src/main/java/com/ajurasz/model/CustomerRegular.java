package com.ajurasz.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.core.style.ToStringCreator;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

/**
 * @author Arek Jurasz
 */
@Entity
@PrimaryKeyJoinColumn(name = "id")
@Table(name = "customers_regular")
public class CustomerRegular extends Customer {

    @NotEmpty(message = "{customer.first-name-required}")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotEmpty(message = "{customer.last-name-required}")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    //@Digits(integer = 9, fraction = 0, message = "{customer.pesel}")
    private String pesel;


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

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("lastName", this.getLastName())
                .append("firstName", this.getFirstName())
                .append("email", this.getEmail())
                .toString();
    }
}
