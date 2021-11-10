package ua.goit.console.commands;

import ua.goit.console.Command;
import ua.goit.dao.*;
import ua.goit.model.*;

import java.util.Optional;
import java.util.function.Consumer;

public class DevelopersCommand implements Command {

    private static final DeveloperDao dao = DeveloperDao.getInstance();
    private static final SkillDao skillDao = SkillDao.getInstance();

    @Override
    public void handle(String params, Consumer<Command> setActive) {
        String command = params.split(" ")[0];

        String subParams = params.replace(command + " ", "");
        switch (command) {
            case "getAll" -> getAll();
            case "get" -> get(subParams);
            case "getSumSalaryByProjectId" -> getSalary(subParams);
            case "projectDevelopersByProjectId" -> getDevelopersByProjectId(subParams);
            case "listDevelopersBySkillId" -> getDevelopersBySkill(subParams);
            case "listDevelopersBySkillBranch" -> getDevelopersBySkillBranch(subParams);
            case "listDevelopersBySkillLevel" -> getDevelopersBySkillLevel(subParams);
            case "create" -> create(subParams);
            case "delete" -> delete(subParams);
            case "update" -> update(subParams);
        }
    }

    private void getSalary(String params) {
        String paramsArray = params.split(" ")[0];

        if (paramsArray.length() == 0) return;

        long id = 1L;

        try {
            id = Long.parseLong(paramsArray);
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong ID format***\n\tDefault ID = 1");
        }

        int sum = dao.getDevelopersByProjectId(id)
                .stream()
                .map(Developer::getSalary)
                .reduce(0, Integer::sum);

        System.out.printf("Sum salaries of all project developers by project_ID [%s]: %d\n", params.split(" ")[0], sum);
    }

    private void getDevelopersByProjectId(String params) {
        String paramsArray = params.split(" ")[0];

        if (paramsArray.length() == 0) return;

        Long projectId = 1L;

        try {
            projectId = Long.parseLong(paramsArray);
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong ID format.***\n\tDefault ID = 1");
        }

        Optional<Project> project = ProjectDao.getInstance().get(projectId);

        if (project.isPresent()) {
            System.out.println("Project: " + project.get());

            dao
                .getDevelopersByProjectId(projectId)
                .forEach(System.out::println);
        } else {
            System.out.printf("Project with ID=%d not found.\n", projectId);
        }
    }

    private void getDevelopersBySkill(String params) {
        String paramsArray = params.split(" ")[0];

        if (paramsArray.length() == 0) return;

        Long skillId;

        try {
            skillId = Long.parseLong(paramsArray);
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong ID format.***\n\tDefault ID = 0");
            return;
        }

        Optional<Skill> skill = skillDao.get(skillId);

        if (skill.isPresent()) {

            System.out.println("Skill: " + skill.get());

            dao.getDevelopersBySkill(
                    skillDao.getAll()
                            .stream()
                            .map(Skill::getId)
                            .filter(id -> id.equals(skillId))
                            .toArray(Long[]::new)
            ).forEach(System.out::println);
        } else {
            System.out.printf("Skill with ID=%d not found.\n", skillId);
        }
    }

    private void getDevelopersBySkillBranch(String params) {
        String branch = params.split(" ")[0];

        if (branch.length() == 0) return;

        dao.getDevelopersBySkill(
                skillDao.getAll()
                        .stream()
                        .filter(s -> s.getBranch().equalsIgnoreCase(branch))
                        .map(Skill::getId)
                        .toArray(Long[]::new)
        ).forEach(System.out::println);
    }

    private void getDevelopersBySkillLevel(String params) {
        String skillLevel = params.split(" ")[0];

        if (skillLevel.length() == 0) return;

        System.out.println("\nSkills by skill_level:");
        skillDao.getAll()
                .stream()
                .filter(s -> s.getSkillLevel().equalsIgnoreCase(skillLevel))
                .forEach(System.out::println);

        System.out.println("\nDevelopers:");

        dao.getDevelopersBySkill(
                skillDao.getAll()
                        .stream()
                        .filter(s -> s.getSkillLevel().equalsIgnoreCase(skillLevel))
                        .map(Skill::getId)
                        .toArray(Long[]::new)
        ).forEach(System.out::println);
    }

    private void create(String params) { // developers create NAME [age] [gender] [description] [salary]
        String[] paramsArray = params.split(" ");

        if (paramsArray.length == 0) return;

        Developer developer = new Developer(1L, paramsArray[0]);

        try {
            developer.setAge(Integer.parseInt(paramsArray[1]));
            developer.setGender(paramsArray[2]);
            developer.setDescription(paramsArray[3]);
            developer.setSalary(Integer.parseInt(paramsArray[4]));
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException ignore) {
        }

        Optional<Developer> createdEntity = dao.create(developer);

        if (createdEntity.isPresent()) {
            System.out.println("Record was created.");
        } else {
            System.out.println("Record not created.");
        }
    }

    private void get(String params) {  // developers get ID
        String paramsArray = params.split(" ")[0];

        if (paramsArray.length() == 0) return;

        long id = 1L;

        try {
            id = Long.parseLong(paramsArray);
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong ID format***\n\tDefault ID = 1");
        }

        Optional<Developer> developer = dao.get(id);

        if (developer.isPresent()) {
            System.out.println(developer.get());
        } else {
            System.out.printf("Developer with ID=%d not found.\n", id);
        }
    }

    private void getAll() { // developers getAll
        dao.getAll().forEach(System.out::println);
    }

    private void delete(String params) {    // developers delete ID
        String[] paramsArray = params.split(" ");

        if (paramsArray.length == 0) return;

        long developerID;

        try {
            developerID = Long.parseLong(paramsArray[0]);
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong ID format***");
            return;
        }

        dao.get(developerID).ifPresent(developer -> {
            dao.delete(developer.getId());
            System.out.println("Record was deleted.");
        });
    }

    private void update(String params) {    // developers update ID NAME [age] [gender] [description] [salary]
        String[] paramsArray = params.split(" ");

        if (paramsArray.length == 0) return;

        long id;

        try {
            id = Long.parseLong(paramsArray[0]);
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong ID format***");
            return;
        }

        Optional<Developer> developer = dao.get(id);

        if (developer.isPresent()) {
            Developer result = null;

            try {
                result = new Developer(developer.get().getId(), paramsArray[1]);

                result.setAge(Integer.parseInt(paramsArray[2]));
                result.setGender(paramsArray[3]);
                result.setDescription(paramsArray[4]);
                result.setSalary(Integer.parseInt(paramsArray[5]));
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException ignore) {
            }

            int update = dao.update(result);

            if (update > 0) {
                System.out.println("Record was updated.");
            } else {
                System.out.println("Record not updated.");
            }
        } else {
            System.out.printf("Developer with ID %s not found.\n", paramsArray[0]);
        }
    }

    @Override
    public void printActiveMenu() {
        System.out.println("--------Developer menu--------");
        System.out.println("Commands: ");
        System.out.println("\t* create NAME [age] [gender] [description] [salary]");
        System.out.println("\t* get ID");
        System.out.println("\t* getSumSalaryByProjectId PROJECT_ID");
        System.out.println("\t* projectDevelopersByProjectId PROJECT_ID");
        System.out.println("\t* listDevelopersBySkillId SKILL_ID");
        System.out.println("\t* listDevelopersBySkillBranch SKILL_BRANCH");
        System.out.println("\t* listDevelopersBySkillLevel SKILL_LEVEL");
        System.out.println("\t* getAll");
        System.out.println("\t* update ID NAME [age] [gender] [description] [salary]");
        System.out.println("\t* delete ID");
    }
}
