package ua.goit;

import java.util.List;

public final class InitDbQueries {
    public static String sql1 = "create table developers(id serial primary key,name varchar(50) not null,age integer,gender varchar(6),description varchar(250),salary integer default 0)";
    public static String sql2 = "create table companies(id serial primary key,name varchar(50) not null,description varchar(250))";
    public static String sql3 = "create table projects (id serial primary key,company_id integer not null,name varchar(50) not null,description varchar(150),cost integer default 0,constraint company_id_fk foreign key (company_id) references companies(id))";
    public static String sql4 = "create table customers(id serial primary key,name varchar(50) not null,description varchar(250))";
    public static String sql5 = "create table skills(id serial primary key,branch varchar(20) not null,skill_level varchar(20) not null)";
    public static String sql6 = "create table developer_skills(developer_id integer not null,skill_id integer not null,constraint developer_id_fk foreign key (developer_id) references developers(id),constraint skill_id_fk foreign key (skill_id) references skills(id))";
    public static String sql7 = "create table project_developers(developer_id integer not null,project_id integer not null,constraint developer_id_fk foreign key (developer_id) references developers(id),constraint project_id_fk foreign key (project_id) references projects(id))";
    public static String sql8 = "create table developer_companies(developer_id integer not null,company_id integer not null,constraint developer_id_fk foreign key (developer_id) references developers(id),constraint company_id_fk foreign key (company_id) references companies(id))";
    public static String sql9 = "create table customers_projects(customer_id integer not null,project_id integer not null,constraint customer_id_fk foreign key (customer_id) references customers(id),constraint project_id_fk foreign key (project_id) references projects(id))";


    public static List<String> getAllQueries() {
        return List.of(sql1, sql2, sql3, sql4, sql5, sql6, sql7, sql8, sql9);
    }

    private InitDbQueries(){}
}
