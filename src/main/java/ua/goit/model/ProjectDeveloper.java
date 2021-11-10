package ua.goit.model;

import ua.goit.dao.AbstractDao.Identity;

public class ProjectDeveloper implements Identity {
    private final Developer developer;
    private final Project project;

    public ProjectDeveloper(Developer developer, Project project) {
        this.developer = developer;
        this.project = project;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public Project getProject() {
        return project;
    }

    @Override
    public Long getId() {
        return 0L;
    }
}
