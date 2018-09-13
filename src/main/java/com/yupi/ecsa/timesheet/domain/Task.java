package com.yupi.ecsa.timesheet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Task.
 */
@Entity
@Table(name = "task")
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "planned_hours", precision = 10, scale = 2)
    private BigDecimal plannedHours;

    @NotNull
    @Column(name = "odoo_id", nullable = false)
    private Long odooId;

    @Column(name = "kanban_state")
    private String kanbanState;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "date_deadline")
    private LocalDate dateDeadline;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Project project;

    @ManyToOne
    @JsonIgnoreProperties("")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Task name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPlannedHours() {
        return plannedHours;
    }

    public Task plannedHours(BigDecimal plannedHours) {
        this.plannedHours = plannedHours;
        return this;
    }

    public void setPlannedHours(BigDecimal plannedHours) {
        this.plannedHours = plannedHours;
    }

    public Long getOdooId() {
        return odooId;
    }

    public Task odooId(Long odooId) {
        this.odooId = odooId;
        return this;
    }

    public void setOdooId(Long odooId) {
        this.odooId = odooId;
    }

    public String getKanbanState() {
        return kanbanState;
    }

    public Task kanbanState(String kanbanState) {
        this.kanbanState = kanbanState;
        return this;
    }

    public void setKanbanState(String kanbanState) {
        this.kanbanState = kanbanState;
    }

    public Boolean isActive() {
        return active;
    }

    public Task active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDate getDateDeadline() {
        return dateDeadline;
    }

    public Task dateDeadline(LocalDate dateDeadline) {
        this.dateDeadline = dateDeadline;
        return this;
    }

    public void setDateDeadline(LocalDate dateDeadline) {
        this.dateDeadline = dateDeadline;
    }

    public Project getProject() {
        return project;
    }

    public Task project(Project project) {
        this.project = project;
        return this;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public Task user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
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
        Task task = (Task) o;
        if (task.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), task.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Task{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", plannedHours=" + getPlannedHours() +
            ", odooId=" + getOdooId() +
            ", kanbanState='" + getKanbanState() + "'" +
            ", active='" + isActive() + "'" +
            ", dateDeadline='" + getDateDeadline() + "'" +
            "}";
    }
}
