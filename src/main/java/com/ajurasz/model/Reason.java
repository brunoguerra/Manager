package com.ajurasz.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.List;

/**
 * @author Arek Jurasz
 */
@Entity
@Table(name = "reasons")
public class Reason extends BaseEntity {

    @Column(nullable = false)
    @NotEmpty
    private String description;

    private Boolean isExcise;

    private Boolean isHomeUse;

    @ManyToOne
    private Company company;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isExcise() {
        return isExcise;
    }

    public void setExcise(boolean excise) {
        isExcise = excise;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public boolean isHomeUse() {
        return isHomeUse;
    }

    public void setHomeUse(boolean isHomeUse) {
        this.isHomeUse = isHomeUse;
    }
}
