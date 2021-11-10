package ua.goit;

import ua.goit.console.CommandHandler;
import ua.goit.dao.config.DataSourceHolder;
import ua.goit.dao.config.ScriptRunner;

import java.io.*;
import java.sql.SQLException;

public class Client {

    public static void main(String[] args) {
//        Для инициализации ДБ.
//        Параметры ДБ в файле --> src/main/resources/application.properties <--
        initDataBase();


//          Home-work queries

//        developers
//        getSumSalaryByProjectId 1
//        projectDevelopersByProjectId 1
//        listDevelopersBySkillBranch java
//        listDevelopersBySkillLevel middle
//        main

//        projects
//        getAllAndFormattedPrint
//        exit


        System.out.println("Start application");

        runMainApp();

        System.out.println("END application");
    }

    private static void initDataBase() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/db/migration/initDB.sql"))) {
            new ScriptRunner(DataSourceHolder.getDataSource().getConnection(),
                    false,
                    true).runScript(reader);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static void runMainApp() {
        CommandHandler commandHandler = new CommandHandler();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                commandHandler.handleCommand(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
