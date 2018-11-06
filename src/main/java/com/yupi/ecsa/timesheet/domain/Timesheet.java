package com.yupi.ecsa.timesheet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yupi.ecsa.timesheet.xmlrpc.OdooEntity;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Objects;

/**
 * A Timesheet.
 */
@Entity
@Table(name = "timesheet")
public class Timesheet implements Serializable, OdooEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private LocalDate date;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "unit_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal unitAmount;

    @Column(name = "odoo_id")
    private Long odooId;

    @ManyToOne
    @JsonIgnoreProperties("")
    private User user;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Task task;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Timesheet date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public Timesheet name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getUnitAmount() {
        return unitAmount;
    }

    public Timesheet unitAmount(BigDecimal unitAmount) {
        this.unitAmount = unitAmount;
        return this;
    }

    public void setUnitAmount(BigDecimal unitAmount) {
        this.unitAmount = unitAmount;
    }

    public Long getOdooId() {
        return odooId;
    }

    public Timesheet odooId(Long odooId) {
        this.odooId = odooId;
        return this;
    }

    public void setOdooId(Long odooId) {
        this.odooId = odooId;
    }

    public User getUser() {
        return user;
    }

    public Timesheet user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task getTask() {
        return task;
    }

    public Timesheet task(Task task) {
        this.task = task;
        return this;
    }

    public void setTask(Task task) {
        this.task = task;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Timesheet timesheet = (Timesheet) o;
        if (timesheet.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), timesheet.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Timesheet{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", name='" + getName() + "'" +
            ", unitAmount=" + getUnitAmount() +
            ", odooId=" + getOdooId() +
            "}";
    }

    @Override
    public HashMap getVals() {
        return new HashMap() {{
            put("date", getDate().format(DateTimeFormatter.ISO_DATE));
            put("name", getName());
            put("unitAmount", getUnitAmount());
            put("external_partner_id", getUser().getPartner().getOdooId());
            put("project_id", getTask().getProject().getOdooId());
            put("task_id", getTask().getOdooId());
        }};
    }
}
