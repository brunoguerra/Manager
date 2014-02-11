package com.ajurasz.model;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
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
    private BigDecimal priceGrossExcise;
    private BigDecimal priceNetExcise;

    @NotNull(message = "{order-details.validation.quantity-required}", groups = {Order.Document.class})
    private BigDecimal quantity;

    @ManyToOne
    private Order order;

    @NotNull(groups = {Order.Document.class})
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

    public BigDecimal getPriceGrossExcise() {
        return priceGrossExcise;
    }

    public void setPriceGrossExcise(BigDecimal priceGrossExcise) {
        this.priceGrossExcise = priceGrossExcise;
    }

    public BigDecimal getPriceNetExcise() {
        return priceNetExcise;
    }

    public void setPriceNetExcise(BigDecimal priceNetExcise) {
        this.priceNetExcise = priceNetExcise;
    }
}
