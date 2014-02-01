package com.ajurasz.model;

import com.ajurasz.util.annotation.FieldMatch;
import com.ajurasz.util.annotation.UniqueEmail;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.*;

/**
 * @author Arek Jurasz
 */
@FieldMatch(first = "password", second = "confirmPassword", errorMessage = "{company.validation.passwords-dont-match}")
@Entity
@Table(name = "companies")
public class Company extends BaseEntity implements UserDetails {

    @UniqueEmail(message = "{comapny.validation.email-unique}")
    @NotEmpty(message = "{comapny.validation.email-required}")
    @Email(message = "{comapny.validation.email-invalid}")
    @Column(name = "email", nullable = false, unique = true)
    private String username;

    @NotEmpty(message = "{comapny.validation.password-required}")
    @Column(nullable = false)
    private String password;

    @NotEmpty(message = "{company.validation.password-confirm-required}")
    @Column(name = "password_confirm", nullable = false)
    private String confirmPassword;

    private Boolean accountNonExpired;
    private Boolean accountNonLocked;
    private Boolean credentialsNonExpired;
    private Boolean enabled;

    @NotEmpty(message = "{company.validation.company-name-required}")
    @Column(name = "company_name", nullable = false)
    private String fullName;

    @Column(name = "owner_first_name")
    private String ownerFirstName;

    @Column(name = "owner_last_name")
    private String ownerLastName;

    @NotEmpty(message = "{company.validation.company-nip-required}")
    @Column(nullable = false)
    private String nip;

    private String bank;

    @Column(name = "bank_numer")
    private String bankNumber;

    @Column(name = "phone_number")
    private String phoneNumber;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Role> roles;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Valid
    private Address address;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    public Company() {
    }

    public Company(String username, String password, List<Role> roles) {
        this(username, password, false, true, true, true, roles);
    }

    public Company(String username, String password, boolean enabled, boolean accountNonExpired,
                boolean credentialsNonExpired, boolean accountNonLocked, List<Role> roles) {

        if (((username == null) || "".equals(username)) || (password == null)) {
            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
        }

        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.roles = roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Role> getRoles() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    public void setAccountNonExpired(boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getOwnerFirstName() {
        if(ownerFirstName == null)
            return "";
        return ownerFirstName;
    }

    public void setOwnerFirstName(String ownerFirstName) {
        this.ownerFirstName = ownerFirstName;
    }

    public String getOwnerLastName() {
        if(ownerLastName == null)
            return "";
        return ownerLastName;
    }

    public void setOwnerLastName(String ownerLastName) {
        this.ownerLastName = ownerLastName;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "Company{" +
                "username='" + username + '\'' +
                ", accountNonExpired=" + accountNonExpired +
                ", accountNonLocked=" + accountNonLocked +
                ", credentialsNonExpired=" + credentialsNonExpired +
                ", enabled=" + enabled +
                ", fullName='" + fullName + '\'' +
                ", ownerFirstName='" + ownerFirstName + '\'' +
                ", ownerLastName='" + ownerLastName + '\'' +
                ", nip='" + nip + '\'' +
                ", bank='" + bank + '\'' +
                ", bankNumber='" + bankNumber + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", roles=" + roles +
                ", address=" + address +
                '}';
    }
}
