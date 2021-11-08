create table developers (
id serial primary key,
name varchar(50) not null,
age integer,
gender varchar(6),
description varchar(250),
salary integer default 0
);

create table companies (
id serial primary key,
name varchar(50) not null,
description varchar(250)
);

create table projects (
id serial primary key,
company_id integer not null,
name varchar(50) not null,
description varchar(150),
cost integer default 0,
creation_date date default current_timestamp,

constraint company_id_fk foreign key (company_id) references companies(id)
);

create table customers (
id serial primary key,
name varchar(50) not null,
description varchar(250)
);

create table skills (
id serial primary key,
branch varchar(20) not null,
skill_level varchar(20) not null
);

create table developer_skills (
developer_id integer not null,
skill_id integer not null,

constraint developer_id_fk foreign key (developer_id) references developers(id),
constraint skill_id_fk foreign key (skill_id) references skills(id)
);

create table project_developers (
developer_id integer not null,
project_id integer not null,

constraint developer_id_fk foreign key (developer_id) references developers(id),
constraint project_id_fk foreign key (project_id) references projects(id)
);

create table developer_companies (
developer_id integer not null,
company_id integer not null,

constraint developer_id_fk foreign key (developer_id) references developers(id),
constraint company_id_fk foreign key (company_id) references companies(id)
);

create table customers_projects (
customer_id integer not null,
project_id integer not null,

constraint customer_id_fk foreign key (customer_id) references customers(id),
constraint project_id_fk foreign key (project_id) references projects(id)
);