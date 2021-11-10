package ua.goit.console.commands;

import ua.goit.console.Command;
import ua.goit.dao.SkillDao;
import ua.goit.model.Skill;

import java.util.Optional;
import java.util.function.Consumer;

public class SkillsCommand implements Command {

    private static final SkillDao dao = SkillDao.getInstance();

    @Override
    public void handle(String params, Consumer<Command> setActive) {
        String command = params.split(" ")[0];

        String subParams = params.replace(command + " ", "");
        switch (command) {
            case "getAll" -> getAll();
            case "get" -> get(subParams);
            case "create" -> create(subParams);
            case "delete" -> delete(subParams);
            case "update" -> update(subParams);
        }
    }

    private void create(String params) { // skills create BRANCH SKILL_LEVEL
        String[] paramsArray = params.split(" ");
        Skill newSkill = new Skill(1L, paramsArray[0], paramsArray[1]);

        Optional<Skill> createdEntity = dao.create(newSkill);

        if (createdEntity.isPresent()) {
            System.out.println("Record was created.");
        } else {
            System.out.println("Record not created.");
        }
    }

    private void get(String params) {  // developers get ID
        String[] s = params.split(" ");

        dao.get(Long.parseLong(s[0])).ifPresent(System.out::println);
    }

    private void getAll() { // developers getAll
        dao.getAll().forEach(System.out::println);
    }

    private void delete(String params) {    // developers delete ID
        String[] paramsArray = params.split(" ");

        dao.get(Long.parseLong(paramsArray[0])).ifPresent(skill -> {
            dao.delete(skill.getId());
            System.out.println("Record was deleted.");
        });
    }

    private void update(String params) {    // developers update ID BRANCH SKILL_LEVEL
        String[] paramsArray = params.split(" ");

        Optional<Skill> skill = dao.get(Long.parseLong(paramsArray[0]));

        if (skill.isPresent()) {
            Skill result = new Skill(skill.get().getId(), paramsArray[0], paramsArray[1]);

            int update = dao.update(result);

            if (update > 0) {
                System.out.println("Record was created.");
            } else {
                System.out.println("Record not created.");
            }
        } else {
            System.out.printf("Developer with ID %s not found.\n", paramsArray[0]);
        }
    }

    @Override
    public void printActiveMenu() {
        System.out.println("-------Skills menu-------");
        System.out.println("Commands: ");
        System.out.println("\t* create BRANCH SKILL_LEVEL");
        System.out.println("\t* get ID");
        System.out.println("\t* getAll");
        System.out.println("\t* update ID BRANCH SKILL_LEVEL");
        System.out.println("\t* delete ID");
    }
}

