package com.ajurasz.model;

/**
 * @author Arek Jurasz
 */
public enum ItemType {
    COAL("Węgiel"),
    CONSTRUCTION("Artykuł budowlany"),
    SERVICE("Usługa");

    private String name;
    private ItemType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
