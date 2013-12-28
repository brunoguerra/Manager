package com.ajurasz.model;

import java.util.List;

/**
 * @author Arek Jurasz
 */
public class OrderFormModel {

    private Order order;
    private List<Item> items;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
