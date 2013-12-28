package com.ajurasz.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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

    private boolean isExcise;

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
}
