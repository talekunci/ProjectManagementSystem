package ua.goit.model;

import ua.goit.dao.AbstractDao.Identity;

public class DeveloperSkill implements Identity {
    private final Developer developer;
    private final Skill skill;

    public DeveloperSkill(Developer developer, Skill skill) {
        this.developer = developer;
        this.skill = skill;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public Skill getSkill() {
        return skill;
    }

    @Override
    public Long getId() {
        return 0L;
    }
}
