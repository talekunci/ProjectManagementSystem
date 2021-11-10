package ua.goit.console.commands;

import ua.goit.console.Command;
import ua.goit.dao.*;
import ua.goit.model.*;

import java.util.Optional;
import java.util.function.Consumer;

public class DeveloperSkillsCommand implements Command {

    private static final DeveloperSkillsDao dao = DeveloperSkillsDao.getInstance();

    @Override
    public void handle(String params, Consumer<Command> setActive) {
        String command = params.split(" ")[0];

        String subParams = params.replace(command + " ", "");
        if ("create" .equals(command)) {
            create(subParams);
        }
    }

    private void create(String params) { // project_developers create DEVELOPER_ID SKILL_ID
        String[] paramsArray = params.split(" ");

        if (paramsArray.length < 2) return;

        DeveloperSkill developerSkill;

        try {
            Optional<Developer> developer = DeveloperDao.getInstance().get(Long.parseLong(paramsArray[0]));
            Optional<Skill> skill = SkillDao.getInstance().get(Long.parseLong(paramsArray[1]));

            if (developer.isEmpty() || skill.isEmpty()) return;

             developerSkill = new DeveloperSkill(developer.get(), skill.get());
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong number format***");
            return;
        }

        dao.create(developerSkill);
        System.out.println("Record was created.");
    }

    @Override
    public void printActiveMenu() {
        System.out.println("--------Developer skills menu--------");
        System.out.println("Commands:");
        System.out.println("\t*create DEVELOPER_ID SKILL_ID");
    }
}
