package ua.goit.model;

import ua.goit.dao.AbstractDao.Identity;

import java.util.Objects;

public class Developer implements Identity {
    private Long id;
    private String name;
    private int age;
    private String gender;
    private String description;
    private int salary;

    public Developer(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Developer(Long id, String name, int age, String gender, String description, Integer salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.description = description;
        this.salary = salary;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Developer developer = (Developer) o;
        return getAge() == developer.getAge()
                && getSalary() == developer.getSalary()
                && getId().equals(developer.getId())
                && getName().equals(developer.getName())
                && Objects.equals(getGender(), developer.getGender())
                && Objects.equals(getDescription(), developer.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getAge(), getGender(), getDescription(), getSalary());
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", description='" + description + '\'' +
                ", salary=" + salary +
                '}';
    }
}
