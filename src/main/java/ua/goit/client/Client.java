package ua.goit.client;

import ua.goit.dao.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client implements Runnable {

    private final DeveloperDao developerDao = DeveloperDao.getInstance();
    private final ProjectDao projectDao = ProjectDao.getInstance();
    private final CompanyDao companyDao = CompanyDao.getInstance();
    private final CustomerDao customerDao = CustomerDao.getInstance();
    private final SkillDao skillDao = SkillDao.getInstance();

    public static void main(String[] args) {
        new Thread(new Client()).start();
//        create developer 1 John JavaDev
    }

    @Override
    public void run() {
        System.out.println("\tQuery format");
        System.out.println("(command) (table name) [sql]");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            while((line = reader.readLine()) != null) {
                if (line.equals("exit")) break;

                String command = line.substring(0, line.indexOf(" "));
                String sql = line.substring(line.indexOf(" ") + 1);

                switch (command) {
                    case "create" -> processCreate(sql);
                    case "get" -> processGet(sql);
                    case "getAll" -> processGetAll(sql);
                    case "update" -> processUpdate(sql);
                    case "delete" -> processDelete(sql);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processCreate(String sql) {
        String tableName = sql.substring(0, sql.indexOf(" ")).toLowerCase();

        sql = sql.substring(sql.indexOf(" ") + 1);
        switch (tableName) {
            case "developer" -> developerDao.createEntity(sql).ifPresent(developerDao::create);
            case "skill" -> skillDao.createEntity(sql).ifPresent(skillDao::create);
            case "project" -> projectDao.createEntity(sql).ifPresent(projectDao::create);
            case "customer" -> customerDao.createEntity(sql).ifPresent(customerDao::create);
            case "company" -> companyDao.createEntity(sql).ifPresent(companyDao::create);
        }
    }

    private void processGet(String sql) {
        String tableName = sql.substring(0, sql.indexOf(" ")).toLowerCase();

        sql = sql.substring(sql.indexOf(" ") + 1);
        if (sql.contains(" ")) sql = sql.substring(0, sql.indexOf(" "));

        Long id = Long.parseLong(sql);
        switch (tableName) {
            case "developer" -> developerDao.get(id).ifPresent(System.out::println);
            case "skill" -> skillDao.get(id).ifPresent(System.out::println);
            case "project" -> projectDao.get(id).ifPresent(System.out::println);
            case "customer" -> customerDao.get(id).ifPresent(System.out::println);
            case "company" -> companyDao.get(id).ifPresent(System.out::println);
        }
    }

    private void processGetAll(String sql) {
        if (sql.contains(" ")) sql = sql.substring(0, sql.indexOf(" ")).toLowerCase();

        switch (sql) {
            case "developer" -> developerDao.getAll().forEach(System.out::println);
            case "skill" -> skillDao.getAll().forEach(System.out::println);
            case "project" -> projectDao.getAll().forEach(System.out::println);
            case "customer" -> customerDao.getAll().forEach(System.out::println);
            case "company" -> companyDao.getAll().forEach(System.out::println);
        }
    }

    private void processUpdate(String sql) {
        String tableName = sql.substring(0, sql.indexOf(" ")).toLowerCase();

        sql = sql.substring(sql.indexOf(" ") + 1);
        switch (tableName) {
            case "developer" -> developerDao.createEntity(sql).ifPresent(developerDao::update);
            case "skill" -> skillDao.createEntity(sql).ifPresent(skillDao::update);
            case "project" -> projectDao.createEntity(sql).ifPresent(projectDao::update);
            case "customer" -> customerDao.createEntity(sql).ifPresent(customerDao::update);
            case "company" -> companyDao.createEntity(sql).ifPresent(companyDao::update);
        }
    }

    private void processDelete(String sql) {
        String tableName = sql.substring(0, sql.indexOf(" ")).toLowerCase();

        Long id = Long.parseLong(sql.substring(sql.indexOf(" ") + 1));
        switch (tableName) {
            case "developer" -> developerDao.delete(id);
            case "skill" -> skillDao.delete(id);
            case "project" -> projectDao.delete(id);
            case "customer" -> customerDao.delete(id);
            case "company" -> companyDao.delete(id);
        }
    }
}
