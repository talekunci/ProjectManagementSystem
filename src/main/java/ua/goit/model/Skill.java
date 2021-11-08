package ua.goit.model;

import ua.goit.dao.AbstractDao.Identity;

import java.util.Objects;

public class Skill implements Identity {
    private Long id;
    private String branch;
    private String skillLevel;

    public Skill(Long id, String branch, String skillLevel) {
        this.id = id;
        this.branch = branch;
        this.skillLevel = skillLevel;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Skill skill = (Skill) o;
        return getId().equals(skill.getId())
                && getBranch().equals(skill.getBranch())
                && getSkillLevel().equals(skill.getSkillLevel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getBranch(), getSkillLevel());
    }

    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", branch='" + branch + '\'' +
                ", skillLevel='" + skillLevel + '\'' +
                '}';
    }
}
