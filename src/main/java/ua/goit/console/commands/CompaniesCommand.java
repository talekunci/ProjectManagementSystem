package ua.goit.console.commands;

import ua.goit.console.Command;
import ua.goit.dao.CompanyDao;
import ua.goit.model.Company;

import java.util.Optional;
import java.util.function.Consumer;

public class CompaniesCommand implements Command {

    private static final CompanyDao dao = CompanyDao.getInstance();

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

    private void create(String params) { // company create NAME [description]
        String[] paramsArray = params.split(" ");
        Company company = new Company(1L, paramsArray[0]);

        try {
            company.setDescription(paramsArray[1]);
        } catch (NumberFormatException ignore) {
        }

        Optional<Company> createdEntity = dao.create(company);

        if (createdEntity.isPresent()) {
            System.out.println("Record was created.");
        } else {
            System.out.println("Record not created.");
        }
    }

    private void get(String params) {  // company get ID
        String[] paramsArray = params.split(" ");

        dao.get(Long.parseLong(paramsArray[0])).ifPresent(System.out::println);
    }

    private void getAll() { // company getAll
        dao.getAll().forEach(System.out::println);
    }

    private void delete(String params) {    // company delete ID
        String[] paramsArray = params.split(" ");

        dao.get(Long.parseLong(paramsArray[0])).ifPresent(company -> {
            dao.delete(company.getId());
            System.out.println("Record was deleted.");
        });
    }

    private void update(String params) {    // company update ID NAME [description]
        String[] paramsArray = params.split(" ");

        Optional<Company> company = dao.get(Long.parseLong(paramsArray[0]));

        if (company.isPresent()) {
            Company result = new Company(company.get().getId(), paramsArray[1]);

            try {
                result.setDescription(paramsArray[2]);
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException ignore) {
            }

            int update = dao.update(result);

            if (update > 0) {
                System.out.println("Record was updated.");
            } else {
                System.out.println("Record not updated.");
            }
        } else {
            System.out.printf("Company with ID %s not found.\n", paramsArray[0]);
        }
    }

    @Override
    public void printActiveMenu() {
        System.out.println("--------Company menu--------");
        System.out.println("Commands: ");
        System.out.println("\t* create NAME [description]");
        System.out.println("\t* get ID");
        System.out.println("\t* getAll");
        System.out.println("\t* update ID NAME [description]");
        System.out.println("\t* delete ID");
    }
}
