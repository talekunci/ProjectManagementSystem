package ua.goit.model;

import ua.goit.dao.AbstractDao.Identity;

public class DeveloperCompany implements Identity {
    private final Developer developer;
    private final Company company;

    public DeveloperCompany(Developer developer, Company company) {
        this.developer = developer;
        this.company = company;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public Company getCompany() {
        return company;
    }

    @Override
    public Long getId() {
        return 0L;
    }
}
