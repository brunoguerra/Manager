package com.ajurasz.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * @author Arek Jurasz
 */
@Entity
@PrimaryKeyJoinColumn(name = "id")
@Table(name = "customers_vat")
public class CustomerVat extends Customer {


    @NotEmpty(message = "{customer-vat.name-required}")
    @Column(nullable = false)
    private String name;

    private String nip;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    @Override
    public String toString() {
        return "CustomerVat{" +
                "name='" + name + '\'' +
                ", nip='" + nip + '\'' +
                '}';
    }
}
