package ua.goit.model;

import ua.goit.dao.AbstractDao.Identity;

public class CustomerProject implements Identity {
    private final Customer customer;
    private final Project project;

    public CustomerProject(Customer customer, Project project) {
        this.customer = customer;
        this.project = project;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Project getProject() {
        return project;
    }

    @Override
    public Long getId() {
        return 0L;
    }
}
