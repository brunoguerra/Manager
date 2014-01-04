package com.ajurasz.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * @author Arek Jurasz
 */
@Entity
@Table(name = "customers_vat")
public class CustomerVat extends BaseEntity {
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

}
