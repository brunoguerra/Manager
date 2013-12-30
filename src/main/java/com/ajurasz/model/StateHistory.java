package com.ajurasz.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Arek Jurasz
 */
@Entity
@Table(name = "states_history")
public class StateHistory extends BaseEntity {

    private BigDecimal value;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date date;

    private String description;

    @ManyToOne()
    private State state;

    public StateHistory() {
        this.date = new Date();
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        setValue(value, false);
    }

    public void setValue(BigDecimal value, boolean returned) {
        this.value = value;
        if(this.value.compareTo(new BigDecimal(0)) == 1) {
            this.description = HistoryType.ADDED + " : " + value.intValue() + " kg";
        }
        if(this.value.compareTo(new BigDecimal(0)) == 0) {
            this.description = HistoryType.ADDED + " : " + value.intValue() + " kg";
        }
        if(this.value.compareTo(new BigDecimal(0)) == -1) {
            this.description = HistoryType.REMOVED + " : " + value.abs().intValue() + " kg";
        }

        if(returned) {
            this.description =  HistoryType.RETURNED + " : " + value.abs().intValue() + " kg";
        }
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }

    //todo: change to use translated messages
    private enum HistoryType {
        ADDED("Dodano do stanu magazynoweg"),
        REMOVED("Odjęto od stanu magazynowego"),
        RETURNED("Zwrot z powodu usunięcia dokumentu dostawy");

        private String desc;
        private HistoryType(String desc) {this.desc = desc;}

        @Override
        public String toString() {
            return desc;
        }
    }
}
