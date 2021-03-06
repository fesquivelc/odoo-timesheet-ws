package com.yupi.ecsa.timesheet.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Project.
 */
@Entity
@Table(name = "project")
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "odoo_id", nullable = false)
    private Long odooId;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Partner partner;

    @ManyToMany
    @JoinTable(name = "project_users", joinColumns = @JoinColumn(name = "projects_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"))
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Task> tasks = new HashSet<>();
    // private Long taskCount;

    public Set<Task> getTasks() {
        return this.tasks;
    }

    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not
    // remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isActive() {
        return active;
    }

    public Project active(Boolean active) {
        this.active = active;
        return this;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public Project name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOdooId() {
        return odooId;
    }

    public Project odooId(Long odooId) {
        this.odooId = odooId;
        return this;
    }

    public void setOdooId(Long odooId) {
        this.odooId = odooId;
    }

    public Partner getPartner() {
        return partner;
    }

    public Project partner(Partner partner) {
        this.partner = partner;
        return this;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public Set<User> getUsers() {
        return users;
    }

    public Project users(Set<User> users) {
        this.users = users;
        return this;
    }

    public Project addUsers(User user) {
        this.users.add(user);
        user.getProjects().add(this);
        return this;
    }

    public Project removeUsers(User user) {
        this.users.remove(user);
        user.getProjects().remove(this);
        return this;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and
    // setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Project project = (Project) o;
        if (project.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), project.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Project{" + "id=" + getId() + ", active='" + isActive() + "'" + ", name='" + getName() + "'"
                + ", odooId=" + getOdooId() + "}";
    }
}
