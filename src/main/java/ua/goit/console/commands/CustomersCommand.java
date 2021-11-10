package ua.goit.console.commands;

import ua.goit.console.Command;
import ua.goit.dao.CustomerDao;
import ua.goit.model.Customer;

import java.util.Optional;
import java.util.function.Consumer;

public class CustomersCommand implements Command {
    
    private static final CustomerDao dao = CustomerDao.getInstance();
    
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

    private void create(String params) { // customers create NAME [description]
        String[] paramsArray = params.split(" ");
        Customer customer = new Customer(1L, paramsArray[0]);

        try {
            customer.setDescription(paramsArray[1]);
        } catch (NumberFormatException ignore) {}

        Optional<Customer> createdEntity = dao.create(customer);

        if (createdEntity.isPresent()) {
            System.out.println("Record was created.");
        } else {
            System.out.println("Record not created.");
        }
    }

    private void get(String params) {  // customers get ID
        String[] paramsArray = params.split(" ");

        dao.get(Long.parseLong(paramsArray[0])).ifPresent(System.out::println);
    }

    private void getAll() { // projects getAll
        dao.getAll().forEach(System.out::println);
    }

    private void delete(String params) {    // customers delete ID
        String[] paramsArray = params.split(" ");

        dao.get(Long.parseLong(paramsArray[0])).ifPresent(customer -> {
            dao.delete(customer.getId());
            System.out.println("Record was deleted.");
        });
    }

    private void update(String params) {    // customers update ID NAME [description]
        String[] paramsArray = params.split(" ");

        Optional<Customer> customer = dao.get(Long.parseLong(paramsArray[0]));

        if (customer.isPresent()) {
            Customer result = new Customer(customer.get().getId(), paramsArray[1]);

            try {
                result.setDescription(paramsArray[2]);
            } catch (ArrayIndexOutOfBoundsException ignore) {
            }

            int update = dao.update(result);

            if (update > 0) {
                System.out.println("Record was updated.");
            } else {
                System.out.println("Record not updated.");
            }
        } else {
            System.out.printf("Customer with ID %s not found.\n", paramsArray[0]);
        }
    }

    @Override
    public void printActiveMenu() {
        System.out.println("--------Customer menu--------");
        System.out.println("Commands: ");
        System.out.println("\t* create NAME [description]");
        System.out.println("\t* get ID");
        System.out.println("\t* getAll");
        System.out.println("\t* update ID NAME [description]");
        System.out.println("\t* delete ID");
    }
}
