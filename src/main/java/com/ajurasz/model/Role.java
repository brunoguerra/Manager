package com.ajurasz.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Arek Jurasz
 */
@Entity
@Table(name = "roles")
public class Role extends BaseEntity implements GrantedAuthority {

    private String authority;

    public Role() {
    }

    public Role(String authority) {
        this.authority = authority;
    }

    public Role(Roles roles) {
        this.authority = roles.toString();
    }

    @ManyToOne
    Company company;

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
