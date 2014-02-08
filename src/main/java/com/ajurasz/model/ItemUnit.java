package com.ajurasz.model;

/**
 * @author Arek Jurasz
 */
public enum ItemUnit {
    KG("kg"),
    SZT("szt.");

    private String unit;
    private ItemUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }
}
