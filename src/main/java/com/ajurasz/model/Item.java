package com.ajurasz.model;

import com.ajurasz.util.annotation.UniqueName;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * @author Arek Jurasz
 */
@Entity
@Table(name = "items")
public class Item extends BaseEntity {

    @Column(nullable = false, unique = true)
    @NotEmpty(message = "{item.name}", groups = { Add.class, Update.class })
    @UniqueName(message = "{item.name.exist}", groups = { Add.class })
    private String name;

    @Column(nullable = false)
    @NotNull(message = "{item.code}", groups = { Add.class, Update.class })
    private Integer code;

    @Column(nullable = false)
    @NotNull(message = "{item.value}", groups = { Add.class, Update.class })
    private Double value;

    @Column(nullable = false)
    @NotNull(message = "{item.priceGross}", groups = { Add.class, Update.class })
    private BigDecimal priceGross;

    @Column(nullable = false)
    private BigDecimal priceNet;

    @Column(nullable = false)
    private BigDecimal priceExcise;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Valid
    @NotNull
    private State state;

    public Item() {
        //set default values
        this.name = "Węgiel - ";
        this.code = 2701;
        this.value = 23.8;
    }

    public Item(String name, int code, double value, BigDecimal priceGross, BigDecimal priceNet, BigDecimal priceExcise) {
        this.name = name;
        this.code = code;
        this.value = value;
        this.priceGross = priceGross;
        this.priceNet = priceNet;
        this.priceExcise = priceExcise;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

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

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
        this.state.setItem(this);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public interface Add {}
    public interface Update {}
}
