package ua.goit.console.commands;

import ua.goit.console.Command;
import ua.goit.dao.DeveloperDao;
import ua.goit.dao.ProjectDao;
import ua.goit.model.Project;

import java.sql.Date;
import java.util.Optional;
import java.util.function.Consumer;

public class ProjectsCommand implements Command {

    private static final ProjectDao dao = ProjectDao.getInstance();
    private static final DeveloperDao developerDao = DeveloperDao.getInstance();

    @Override
    public void handle(String params, Consumer<Command> setActive) {
        String command = params.split(" ")[0];

        String subParams = params.replace(command + " ", "");
        switch (command) {
            case "getAll" -> getAll();
            case "getAllAndFormattedPrint" -> getFormatAndPrint();
            case "get" -> get(subParams);
            case "create" -> create(subParams);
            case "delete" -> delete(subParams);
            case "update" -> update(subParams);
        }
    }

    private void create(String params) { // projects create COMPANY_ID NAME [description] [creation_date]
        String[] paramsArray = params.split(" ");

        if (paramsArray.length < 2) return;

        Project project = new Project(1L, Long.parseLong(paramsArray[0]), paramsArray[1]);

        try {
            project.setDescription(paramsArray[2]);
            project.setCreationDate(Date.valueOf(paramsArray[3]));
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException ignore) {
        }

        Optional<Project> createdEntity = dao.create(project);

        if (createdEntity.isPresent()) {
            System.out.println("Record was created.");
        } else {
            System.out.println("Record not created.");
        }
    }

    private void get(String params) {  // projects get ID
        String[] paramsArray = params.split(" ");

        if (paramsArray.length == 0) return;

        long id = 1L;

        try {
            id = Long.parseLong(paramsArray[0]);
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong ID format***\n\tDefault ID = 1");
        }

        Optional<Project> project = dao.get(id);

        if (project.isPresent()) {
            System.out.println(project.get());
        } else {
            System.out.printf("Project with ID=%d not found.\n", id);
        }

    }

    private void getFormatAndPrint() {
        dao.getAll().forEach(project -> System.out.printf(
                "project creation date: %s - project name: %-6s - amount of project developers: %d\n",
                project.getCreationDate().toString(),
                project.getName(),
                developerDao.getDevelopersByProjectId(project.getId()).size()
        ));
    }

    private void getAll() { // projects getAll
        dao.getAll().forEach(System.out::println);
    }

    private void delete(String params) {    // projects delete ID
        String[] paramsArray = params.split(" ");

        if (paramsArray.length == 0) return;

        long id;

        try {
            id = Long.parseLong(paramsArray[0]);
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong ID format***");
            return;
        }

        dao.get(id).ifPresent(project -> {
            dao.delete(project.getId());
            System.out.println("Record was deleted.");
        });

    }

    private void update(String params) {    // projects update ID COMPANY_ID NAME [description] [creation_date]
        String[] paramsArray = params.split(" ");

        if (paramsArray.length < 3) return;

        long projectId;

        try {
            projectId = Long.parseLong(paramsArray[0]);
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong ID format.***");
            return;
        }

        Optional<Project> project = dao.get(projectId);

        if (project.isPresent()) {
            Project result = new Project(project.get().getId(), Long.parseLong(paramsArray[1]), paramsArray[2]);

            try {
                result.setDescription(paramsArray[3]);
                result.setCreationDate(Date.valueOf(paramsArray[4]));
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException ignore) {
            }

            int update = dao.update(result);

            if (update > 0) {
                System.out.println("Record was updated.");
            } else {
                System.out.println("Record not updated.");
            }
        } else {
            System.out.printf("Project with ID %s not found.\n", paramsArray[0]);
        }
    }

    @Override
    public void printActiveMenu() {
        System.out.println("--------Project menu--------");
        System.out.println("Commands: ");
        System.out.println("\t* create COMPANY_ID NAME [description] [creation_date]");
        System.out.println("\t* get ID");
        System.out.println("\t* getAll");
        System.out.println("\t* getAllAndFormattedPrint");
        System.out.println("\t* update ID COMPANY_ID NAME [description] [creation_date]");
        System.out.println("\t* delete ID");
    }
}
