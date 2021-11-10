package ua.goit.console.commands;

import ua.goit.console.Command;
import ua.goit.dao.DeveloperDao;
import ua.goit.model.Developer;

import java.util.Optional;
import java.util.function.Consumer;

public class DevelopersCommand implements Command {

    private static final DeveloperDao dao = DeveloperDao.getInstance();

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

    private void create(String params) { // developers create NAME [age] [gender] [description] [salary]
        String[] s = params.split(" ");
        Developer developer = new Developer(1L, s[0]);

        try {
            developer.setAge(Integer.parseInt(s[1]));
            developer.setGender(s[2]);
            developer.setDescription(s[3]);
            developer.setSalary(Integer.parseInt(s[4]));
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
        String[] s = params.split(" ");

        dao.get(Long.parseLong(s[0])).ifPresent(System.out::println);
    }

    private void getAll() { // developers getAll
        dao.getAll().forEach(System.out::println);
    }

    private void delete(String params) {    // developers delete ID
        String[] s = params.split(" ");

        dao.get(Long.parseLong(s[0])).ifPresent(developer -> {
            dao.delete(developer.getId());
            System.out.println("Record was deleted.");
        });
    }

    private void update(String params) {    // developers update ID NAME [age] [gender] [description] [salary]
        String[] paramsArray = params.split(" ");

        Optional<Developer> developer = dao.get(Long.parseLong(paramsArray[0]));

        if (developer.isPresent()) {
            Developer result = new Developer(developer.get().getId(), paramsArray[1]);

            try {
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
        System.out.println("\t* getAll");
        System.out.println("\t* update ID NAME [age] [gender] [description] [salary]");
        System.out.println("\t* delete ID");
    }
}
