package ua.goit.console.commands;

import ua.goit.console.Command;
import ua.goit.dao.DeveloperDao;
import ua.goit.dao.ProjectDao;
import ua.goit.dao.ProjectDevelopersDao;
import ua.goit.model.Developer;
import ua.goit.model.Project;
import ua.goit.model.ProjectDeveloper;

import java.util.Optional;
import java.util.function.Consumer;

public class ProjectDevelopersCommand implements Command {

    private static final ProjectDevelopersDao dao = ProjectDevelopersDao.getInstance();

    @Override
    public void handle(String params, Consumer<Command> setActive) {
        String command = params.split(" ")[0];

        String subParams = params.replace(command + " ", "");
        if ("create" .equals(command)) {
            create(subParams);
        }
    }

    private void create(String params) { // project_developers create DEVELOPER_ID PROJECT_ID
        String[] paramsArray = params.split(" ");

        if (paramsArray.length < 2) return;

        ProjectDeveloper projectDeveloper;

        try {
            Optional<Developer> developer = DeveloperDao.getInstance().get(Long.parseLong(paramsArray[0]));
            Optional<Project> project = ProjectDao.getInstance().get(Long.parseLong(paramsArray[1]));

            if (developer.isEmpty() || project.isEmpty()) return;

            projectDeveloper = new ProjectDeveloper(developer.get(), project.get());
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong number format***");
            return;
        }

        dao.create(projectDeveloper);
        System.out.println("Record was created.");
    }

    @Override
    public void printActiveMenu() {
        System.out.println("--------Project developers menu--------");
        System.out.println("Commands:");
        System.out.println("\t*create DEVELOPER_ID PROJECT_ID");
    }
}
