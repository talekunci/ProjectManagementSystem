package ua.goit.model;

import ua.goit.dao.AbstractDao.Identity;

import java.sql.Date;
import java.util.Objects;

public class Project implements Identity {
    private Long id;
    private Long companyId;
    private String name;
    private String description;
    private int cost;
    private Date creationDate;

    public Project(Long id, Long companyId, String name) {
        this.id = id;
        this.companyId = companyId;
        this.name = name;
        creationDate = new Date(new java.util.Date().getTime());
    }

    public Project(Long id, Long companyId, String name, String description, int cost, Date creationDate) {
        this.id = id;
        this.companyId = companyId;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.creationDate = creationDate;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return getId().equals(project.getId())
                && getCompanyId().equals(project.getCompanyId())
                && getName().equals(project.getName())
                && Objects.equals(getDescription(), project.getDescription())
                && getCreationDate().equals(project.getCreationDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCompanyId(), getName(), getDescription(), getCreationDate());
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", companyId=" + companyId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", cost=" + cost +
                ", creationDate=" + creationDate +
                '}';
    }
}
