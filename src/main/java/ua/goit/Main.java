package ua.goit;

import ua.goit.dao.*;
import ua.goit.dao.config.DataSourceHolder;
import ua.goit.dao.config.ScriptRunner;
import ua.goit.model.Developer;
import ua.goit.model.Skill;

import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
//        initDb();

//        Initialize Database
        /*System.out.println("*** Database initialization started ***");
        try {
            ScriptRunner scriptRunner = new ScriptRunner(
                    DataSourceHolder.getDataSource().getConnection(),
                    false,
                    true);

            scriptRunner.runScript(new FileReader("src/main/resources/db/migration/initDB.sql"));
            scriptRunner.runScript(new FileReader("src/main/resources/db/migration/populateDB.sql"));
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        System.out.println("*** Database initialization completed ***\n\n");*/

        DeveloperDao developerDao = DeveloperDao.getInstance();
        SkillDao skillDao = SkillDao.getInstance();
        ProjectDao projectDao = ProjectDao.getInstance();


        System.out.println("Sum salaries of all project developers by project_id");
        System.out.println(developerDao.getDevelopersByProjectId(1L)
                .stream()
                .map(Developer::getSalary)
                .reduce(0, Integer::sum)
        );


        System.out.println("\nProject developers by project_id");
        developerDao
                .getDevelopersByProjectId(1L)
                .forEach(System.out::println);


        System.out.println("\nList of Java developers by skill_level");
        developerDao.getDevelopersBySkill(
                skillDao.getAll()
                        .stream()
                        .filter(s -> s.getBranch().equalsIgnoreCase("java"))
                        .map(Skill::getId)
                        .toArray(Long[]::new)
        ).forEach(System.out::println);


        System.out.println("\nList of middle developers by skill_id");
        developerDao.getDevelopersBySkill(
                skillDao.getAll()
                        .stream()
                        .filter(s -> s.getSkillLevel().equalsIgnoreCase("middle"))
                        .map(Skill::getId)
                        .toArray(Long[]::new)
        ).forEach(System.out::println);


//        add creation_date column in projects table
//        SqlExecutor.execute("alter table projects add column creation_date date default current_timestamp", ps -> {});
//        SqlExecutor.execute("update projects set creation_date = '2013-10-29' where name = 'ARA'", ps -> {});
//        SqlExecutor.execute("update projects set creation_date = '2007-11-19' where name = 'Kindle'", ps -> {});
//        SqlExecutor.execute("update projects set creation_date = '2014-11-06' where name = 'Alexa'", ps -> {});

        System.out.println("\nList of projects in {creating_date - project_name - amount of project developers} format");
        projectDao.getAll().forEach(project -> System.out.printf(
                "project creation date: %s - project name: %-6s - amount of project developers: %d\n",
                project.getCreationDate().toString(),
                project.getName(),
                developerDao.getDevelopersByProjectId(project.getId()).size()
        ));
    }

    public static void initDb() {
        for (String sql : InitDbQueries.getAllQueries()) {
            SqlExecutor.execute(sql, ps -> {
            });
        }

        for (String sql : PopulateDbQueries.getAllQueries()) {
            SqlExecutor.execute(sql, ps -> {
            });
        }
    }
}
