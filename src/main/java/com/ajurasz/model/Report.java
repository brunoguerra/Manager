package com.ajurasz.model;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.joda.time.DateTimeFieldType;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Arek Jurasz
 */
@Table(name = "reports")
@Entity
public class Report extends BaseEntity {
    @Column(name = "report_name")
    private String reportName;

    @NotEmpty(message = "{report.quoter}")
    private String quoter;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
    @Column(name = "creation_date")
    private DateTime creationDate;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "start_date")
    @NotNull(message = "{report.startDate}")
    private DateTime startDate;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Column(name = "end_date")
    @NotNull(message = "{report.endDate}")
    private DateTime endDate;

    @ManyToOne
    private Company company;

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        if(startDate == null) {
            this.startDate = startDate;
            return;
        }
        this.startDate = new DateTime(
                startDate.get(DateTimeFieldType.year()),
                startDate.get(DateTimeFieldType.monthOfYear()),
                startDate.get(DateTimeFieldType.dayOfMonth()),
                0, 0, 0, 0
        );
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        if(endDate == null) {
            this.endDate = endDate;
            return;
        }
        this.endDate = new DateTime(
                endDate.get(DateTimeFieldType.year()),
                endDate.get(DateTimeFieldType.monthOfYear()),
                endDate.get(DateTimeFieldType.dayOfMonth()),
                23, 59, 59, 59
        );
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public String getQuoter() {
        return quoter;
    }

    public void setQuoter(String quoter) {
        this.quoter = quoter;
    }
}
