package com.ajurasz.model;

import com.ajurasz.util.annotation.UniqueName;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author Arek Jurasz
 */
@Entity
@Table(name = "items")
public class Item extends BaseEntity {

    @NotEmpty(message = "{item.validation.name-required}",
            groups = {
                    Add_Coal.class, Update_Coal.class,
                    Add_Construction.class, Update_Construction.class,
                    Add_Service.class, Update_Service.class
            })
    @UniqueName(message = "{item.validation.name-unique}",
            groups = { Add_Coal.class, Add_Construction.class, Add_Service.class })
    @Column(nullable = false)
    private String name;

    private String nameInvoice;

    @NotNull(message = "{item.validation.code-required}", groups = { Add_Coal.class, Update_Coal.class })
    @Column(name = "code_cn", nullable = false)
    private Integer code;

    @NotNull(message = "{item.validation.value-required}", groups = { Add_Coal.class, Update_Coal.class })
    @Column(name = "value_kj", nullable = false)
    private Double value;

    @Column(nullable = false)
    @NotNull(message = "{item.validation.priceGross-required}",
            groups = {
                    Add_Coal.class, Update_Coal.class,
                    Add_Construction.class, Update_Construction.class,
                    Add_Service.class, Update_Service.class
            })
    private BigDecimal priceGross;

    @Column(nullable = false)
    private BigDecimal priceNet;

    @Column(nullable = false)
    private BigDecimal priceGrossExcise;

    @Column(nullable = false)
    private BigDecimal priceNetExcise;

    @Column(nullable = false)
    private ItemType type;

    @Column()
    @NotNull(groups = {
            Add_Coal.class, Update_Coal.class,
            Add_Construction.class, Update_Construction.class
    })
    private ItemUnit unit;

    @Column(nullable = false)
    private Vat vat;

    @Valid
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = true)
    private State state;

    @ManyToOne
    private Company company;

    public Item() {
        //set default values
        this.code = 2701;
        this.value = 23.8;
    }

    public Item(String name, int code, double value, BigDecimal priceGross, BigDecimal priceNet,
            BigDecimal priceGrossExcise, BigDecimal priceNetExcise) {
        this.name = name;
        this.code = code;
        this.value = value;
        this.priceGross = priceGross;
        this.priceNet = priceNet;
        this.priceGrossExcise = priceGrossExcise;
        this.priceNetExcise = priceNetExcise;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
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

    public String getNameInvoice() {
        return nameInvoice;
    }

    public void setNameInvoice(String nameInvoice) {
        this.nameInvoice = nameInvoice;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public Vat getVat() {
        return vat;
    }

    public void setVat(Vat vat) {
        this.vat = vat;
    }

    public ItemUnit getUnit() {
        return unit;
    }

    public void setUnit(ItemUnit unit) {
        this.unit = unit;
    }

    //coal validation interfaces
    public interface Add_Coal {}
    public interface Update_Coal {}

    //construction validation interfaces
    public interface Add_Construction {}
    public interface Update_Construction {}

    //service validation interfaces
    public interface Add_Service {}
    public interface Update_Service {}

}
