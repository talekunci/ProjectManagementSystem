package ua.goit.console.commands;

import ua.goit.console.Command;
import ua.goit.dao.CompanyDao;
import ua.goit.dao.DeveloperCompaniesDao;
import ua.goit.dao.DeveloperDao;
import ua.goit.model.*;

import java.util.Optional;
import java.util.function.Consumer;

public class DeveloperCompaniesCommand implements Command {

    private static final DeveloperCompaniesDao dao = DeveloperCompaniesDao.getInstance();

    @Override
    public void handle(String params, Consumer<Command> setActive) {
        String command = params.split(" ")[0];

        String subParams = params.replace(command + " ", "");
        if ("create" .equals(command)) {
            create(subParams);
        }
    }

    private void create(String params) { // developer_companies create DEVELOPER_ID COMPANY_ID
        String[] paramsArray = params.split(" ");

        if (paramsArray.length < 2) return;

        DeveloperCompany developerCompany;

        try {
            Optional<Developer> developer = DeveloperDao.getInstance().get(Long.parseLong(paramsArray[0]));
            Optional<Company> company = CompanyDao.getInstance().get(Long.parseLong(paramsArray[1]));

            if (developer.isEmpty() || company.isEmpty()) return;

            developerCompany = new DeveloperCompany(developer.get(), company.get());
        } catch (NumberFormatException e) {
            System.out.println("\t***Wrong number format***");
            return;
        }

        dao.create(developerCompany);
        System.out.println("Record was created.");
    }

    @Override
    public void printActiveMenu() {
        System.out.println("--------Developer companies menu--------");
        System.out.println("Commands:");
        System.out.println("\t*create DEVELOPER_ID COMPANY_ID");
    }
}
