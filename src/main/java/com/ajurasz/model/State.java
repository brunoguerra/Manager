package com.ajurasz.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Arek Jurasz
 */
@Entity
@Table(name = "states")
public class State extends BaseEntity {

    @Column(name = "current_state")
    @NotNull(message = "{state.currentState}", groups = { Item.Add.class, Item.Update.class })
    @DecimalMin(value = "-9999999.99", groups = { Item.Add.class, Item.Update.class })
    @DecimalMax(value = "9999999.99", groups = { Item.Add.class, Item.Update.class })
    private BigDecimal currentState;

    @Column(name = "last_value")
    @NotNull(groups = { Item.Add.class, Item.Update.class })
    @DecimalMin(value = "-9999999.99", groups = { Item.Add.class, Item.Update.class })
    @DecimalMax(value = "9999999.99", groups = { Item.Add.class, Item.Update.class })
    private BigDecimal lastValue;

    @OneToOne(mappedBy = "state")
    private Item item;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "state", fetch = FetchType.EAGER)
    private List<StateHistory> stateHistories;

    public State() {
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public List<StateHistory> getStateHistories() {
        return stateHistories;
    }

    public void setStateHistories(List<StateHistory> stateHistories) {
        this.stateHistories = stateHistories;
    }

    public BigDecimal getCurrentState() {
        return currentState;
    }

    /**
     * Add or substract passed value from currentState
     * @param currentState
     */
    public void setCurrentState(BigDecimal currentState) {
        if(currentState != null) {
            this.lastValue = currentState;
            if(this.currentState == null) {
                this.currentState = currentState;
            } else {
                this.currentState = this.currentState.add(currentState);
            }
        }  else {
            this.currentState = null;
        }
    }

    public BigDecimal getLastValue() {
        return lastValue;
    }

    public void setLastValue(BigDecimal lastValue) {
        this.lastValue = lastValue;
    }
}
