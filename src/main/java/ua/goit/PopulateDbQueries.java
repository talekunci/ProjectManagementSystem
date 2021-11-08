package ua.goit;

import java.util.List;

public final class PopulateDbQueries {
    public static String sql1 = "insert into developers(name, age, gender) values ('Misha', 16, 'male')";
    public static String sql2 = "insert into developers(name, age, gender) values ('Vera', 35, 'female')";
    public static String sql3 = "insert into developers(name, age, gender) values ('Kolya', 56, 'male')";
    public static String sql4 = "insert into developers(name, age, gender) values ('Anna', 12, 'female')";
    public static String sql5 = "insert into skills(branch, skill_level) values ('Java', 'Junior')";
    public static String sql6 = "insert into skills(branch, skill_level) values ('Java', 'Middle')";
    public static String sql7 = "insert into skills(branch, skill_level) values ('SQL', 'Junior')";
    public static String sql8 = "insert into skills(branch, skill_level) values ('SQL', 'Middle')";
    public static String sql9 = "insert into developer_skills(developer_id, skill_id) values (1, 2)";
    public static String sql10 = "insert into developer_skills(developer_id, skill_id) values (2, 1)";
    public static String sql11 = "insert into developer_skills(developer_id, skill_id) values (3, 1)";
    public static String sql12 = "insert into developer_skills(developer_id, skill_id) values (4, 2)";
    public static String sql13 = "insert into developer_skills(developer_id, skill_id) values (4, (select id from skills where branch = 'SQL' and skill_level = 'Junior'))";
    public static String sql14 = "insert into companies(name) values ('GOOGLE')";
    public static String sql15 = "insert into companies(name) values ('AMAZON')";
    public static String sql16 = "insert into developer_companies(developer_id, company_id) values((select id from developers where name = 'Anna'), (select id from companies where name = 'GOOGLE'))";
    public static String sql17 = "insert into developer_companies(developer_id, company_id) values((select id from developers where name = 'Kolya'), (select id from companies where name = 'GOOGLE'))";
    public static String sql18 = "insert into developer_companies(developer_id, company_id) values((select id from developers where name = 'Misha'), (select id from companies where name = 'AMAZON'))";
    public static String sql19 = "insert into developer_companies(developer_id, company_id) values((select id from developers where name = 'Vera'), (select id from companies where name = 'AMAZON'))";
    public static String sql20 = "insert into projects(company_id, name) values((select id from companies where name = 'GOOGLE'), 'ARA')";
    public static String sql21 = "insert into projects(company_id, name) values((select id from companies where name = 'AMAZON'), 'Kindle')";
    public static String sql22 = "insert into projects(company_id, name) values((select id from companies where name = 'AMAZON'), 'Alexa')";
    public static String sql23 = "insert into customers(name) values ('GOOGLE')";
    public static String sql24 = "insert into customers(name) values ('People')";
    public static String sql25 = "insert into customers_projects(customer_id, project_id) values ((select id from customers where name = 'GOOGLE'), (select id from projects where name = 'ARA'))";
    public static String sql26 = "insert into customers_projects(customer_id, project_id) values ((select id from customers where name = 'People'), (select id from projects where name = 'Kindle'))";
    public static String sql27 = "insert into customers_projects(customer_id, project_id) values ((select id from customers where name = 'People'), (select id from projects where name = 'Alexa'))";
    public static String sql28 = "insert into project_developers(developer_id, project_id) values ((select id from developers where name = 'Anna'), (select id from projects where name = 'ARA'))";
    public static String sql29 = "insert into project_developers(developer_id, project_id) values ((select id from developers where name = 'Kolya'), (select id from projects where name = 'ARA'))";
    public static String sql30 = "insert into project_developers(developer_id, project_id) values ((select id from developers where name = 'Misha'), (select id from projects where name = 'Alexa'))";
    public static String sql31 = "insert into project_developers(developer_id, project_id) values ((select id from developers where name = 'Misha'), (select id from projects where name = 'Kindle'))";
    public static String sql32 = "insert into project_developers(developer_id, project_id) values ((select id from developers where name = 'Vera'), (select id from projects where name = 'Alexa'))";
    public static String sql33 = "update developers d set salary = 300 where salary = 0 and (select skill_id from developer_skills ds where ds.developer_id = d.id limit 1) <> 0";
    public static String sql34 = "update developers d set salary = salary + 200 where d.id = (select developer_id from developer_skills ds join skills s on d.id = ds.developer_id and ds.skill_id = s.id and s.branch = 'Java' and s.skill_level = 'Middle')";
    public static String sql35 = "update developers d set salary = salary + 250 where d.id = (select developer_id from developer_skills ds join skills s on ds.skill_id = s.id and ds.developer_id = d.id and s.branch = 'SQL')";
    public static String sql36 = "update projects p set cost = (select sum(d.salary) from developers d join project_developers pd on d.id = pd.developer_id and pd.project_id = p.id)";

    public static List<String> getAllQueries() {
        return List.of(sql1, sql2, sql3, sql4, sql5, sql6, sql7, sql8, sql9,
                sql10, sql11, sql12, sql13, sql14, sql15, sql16, sql17, sql18, sql19,
                sql20, sql21, sql22, sql23, sql24, sql25, sql26, sql27, sql28, sql29,
                sql30, sql31, sql32, sql33, sql34, sql35, sql36);
    }

    private PopulateDbQueries(){}
}
