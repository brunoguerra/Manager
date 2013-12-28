package com.ajurasz.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author Arek Jurasz
 */
@Entity
@Table(name = "order_details")
public class OrderDetails extends BaseEntity {
    private BigDecimal priceGross;
    private BigDecimal priceNet;
    private BigDecimal priceExcise;

    @NotNull
    private BigDecimal quantity;

    @ManyToOne
    private Order order;

    @ManyToOne()
    private Item item;

    @ManyToOne()
    private Reason reason;

    public BigDecimal getPriceGross() {
        return priceGross;
    }

    public void setPriceGross(BigDecimal priceGross) {
        this.priceGross = priceGross;
    }

    public BigDecimal getPriceNet() {
        return priceNet;
    }

    public void setPriceNet(BigDecimal priceNet) {
        this.priceNet = priceNet;
    }

    public BigDecimal getPriceExcise() {
        return priceExcise;
    }

    public void setPriceExcise(BigDecimal priceExcise) {
        this.priceExcise = priceExcise;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Reason getReason() {
        return reason;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }
}
