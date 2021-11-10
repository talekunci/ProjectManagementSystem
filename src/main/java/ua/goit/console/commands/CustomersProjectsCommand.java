package ua.goit.console.commands;

import ua.goit.console.Command;
import ua.goit.dao.CustomerDao;
import ua.goit.dao.CustomerProjectsDao;
import ua.goit.dao.ProjectDao;
import ua.goit.model.*;

import java.util.Optional;
import java.util.function.Consumer;

public class CustomersProjectsCommand implements Command {

    private static final CustomerProjectsDao dao = CustomerProjectsDao.getInstance();

    @Override
    public void handle(String params, Consumer<Command> setActive) {
        String command = params.split(" ")[0];

        String subParams = params.replace(command + " ", "");
        if ("create" .equals(command)) {
            create(subParams);
        }
    }

    private void create(String params) { // project_developers create CUSTOMER_ID PROJECT_ID
        String[] paramsArray = params.split(" ");

        if (paramsArray.length < 2) return;

        CustomerProject customerProject;

        try {
            Optional<Customer> customer = CustomerDao.getInstance().get(Long.parseLong(paramsArray[0]));
            Optional<Project> project = ProjectDao.getInstance().get(Long.parseLong(paramsArray[1]));

            if (customer.isEmpty() || project.isEmpty()) return;

            customerProject = new CustomerProject(customer.get(), project.get());
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong number format***");
            return;
        }

        dao.create(customerProject);
        System.out.println("Record was created.");
    }

    @Override
    public void printActiveMenu() {
        System.out.println("--------Customers projects menu--------");
        System.out.println("Commands:");
        System.out.println("\t*create CUSTOMER_ID PROJECT_ID");
    }
}
