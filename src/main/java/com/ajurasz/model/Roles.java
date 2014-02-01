package com.ajurasz.model;

/**
 * @author Arek Jurasz
 */
public enum Roles {
    USER("ROLER_USER"),
    VAT("ROLE_VAT"),
    ADMIN("ROLER_ADMIN");

    private String name;
    private Roles(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
