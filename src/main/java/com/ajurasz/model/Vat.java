package com.ajurasz.model;

/**
 * @author Arek Jurasz
 */
public enum Vat {
    _7(7),
    _8(8),
    _23(23);

    private int value;
    private Vat(int value) {
        this.value = value;
    }

    public String getValue() {
        return "" + value + "%";
    }
}
