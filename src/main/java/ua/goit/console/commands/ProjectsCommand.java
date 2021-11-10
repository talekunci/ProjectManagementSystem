package ua.goit.console.commands;

import ua.goit.console.Command;
import ua.goit.dao.ProjectDao;
import ua.goit.model.Project;

import java.sql.Date;
import java.util.Optional;
import java.util.function.Consumer;

public class ProjectsCommand implements Command {

    private static final ProjectDao dao = ProjectDao.getInstance();

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

    private void create(String params) { // projects create COMPANY_ID NAME [description] [creation_date]
        String[] s = params.split(" ");
        Project project = new Project(1L, Long.parseLong(s[0]), s[1]);

        try {
            project.setDescription(s[2]);
            project.setCreationDate(Date.valueOf(s[3]));
        } catch (NumberFormatException ignore) {}

        Optional<Project> createdEntity = dao.create(project);

        if (createdEntity.isPresent()) {
            System.out.println("Record was created.");
        } else {
            System.out.println("Record not created.");
        }
    }

    private void get(String params) {  // projects get ID
        String[] s = params.split(" ");

        dao.get(Long.parseLong(s[0])).ifPresent(System.out::println);
    }

    private void getAll() { // projects getAll
        dao.getAll().forEach(System.out::println);
    }

    private void delete(String params) {    // projects delete ID
        String[] s = params.split(" ");

        dao.get(Long.parseLong(s[0])).ifPresent(project -> {
            dao.delete(project.getId());
            System.out.println("Record was deleted.");
        });
    }

    private void update(String params) {    // projects update ID COMPANY_ID NAME [description] [creation_date]
        String[] paramsArray = params.split(" ");

        Optional<Project> project = dao.get(Long.parseLong(paramsArray[0]));

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
        System.out.println("\t* update ID COMPANY_ID NAME [description] [creation_date]");
        System.out.println("\t* delete ID");
    }
}
