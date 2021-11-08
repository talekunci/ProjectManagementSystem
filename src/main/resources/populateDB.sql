insert into developers(name, age, gender) values ('Misha', 16, 'male');
insert into developers(name, age, gender) values ('Vera', 35, 'female');
insert into developers(name, age, gender) values ('Kolya', 56, 'male');
insert into developers(name, age, gender) values ('Anna', 12, 'female');


insert into skills(branch, skill_level) values ('Java', 'Junior');
insert into skills(branch, skill_level) values ('Java', 'Middle');
insert into skills(branch, skill_level) values ('SQL', 'Junior');
insert into skills(branch, skill_level) values ('SQL', 'Middle');


insert into developer_skills(developer_id, skill_id) values (1, 2);
insert into developer_skills(developer_id, skill_id) values (2, 1);
insert into developer_skills(developer_id, skill_id) values (3, 1);
insert into developer_skills(developer_id, skill_id) values (4, 2);

insert into developer_skills(developer_id, skill_id) values (4, (select id from skills where branch = 'SQL' and skill_level = 'Junior'));


insert into companies(name) values ('GOOGLE');
insert into companies(name) values ('AMAZON');


insert into developer_companies(developer_id, company_id) values((select id from developers where name = 'Anna'), (select id from companies where name = 'GOOGLE'));
insert into developer_companies(developer_id, company_id) values((select id from developers where name = 'Kolya'), (select id from companies where name = 'GOOGLE'));
insert into developer_companies(developer_id, company_id) values((select id from developers where name = 'Misha'), (select id from companies where name = 'AMAZON'));
insert into developer_companies(developer_id, company_id) values((select id from developers where name = 'Vera'), (select id from companies where name = 'AMAZON'));


insert into projects(company_id, name) values((select id from companies where name = 'GOOGLE'), 'ARA');
insert into projects(company_id, name) values((select id from companies where name = 'AMAZON'), 'Kindle');
insert into projects(company_id, name) values((select id from companies where name = 'AMAZON'), 'Alexa');


insert into customers(name) values ('GOOGLE');
insert into customers(name) values ('People');


insert into customers_projects(customer_id, project_id) values ((select id from customers where name = 'GOOGLE'), (select id from projects where name = 'ARA'));
insert into customers_projects(customer_id, project_id) values ((select id from customers where name = 'People'), (select id from projects where name = 'Kindle'));
insert into customers_projects(customer_id, project_id) values ((select id from customers where name = 'People'), (select id from projects where name = 'Alexa'));


insert into project_developers(developer_id, project_id) values ((select id from developers where name = 'Anna'), (select id from projects where name = 'ARA'));
insert into project_developers(developer_id, project_id) values ((select id from developers where name = 'Kolya'), (select id from projects where name = 'ARA'));
insert into project_developers(developer_id, project_id) values ((select id from developers where name = 'Misha'), (select id from projects where name = 'Alexa'));
insert into project_developers(developer_id, project_id) values ((select id from developers where name = 'Misha'), (select id from projects where name = 'Kindle'));
insert into project_developers(developer_id, project_id) values ((select id from developers where name = 'Vera'), (select id from projects where name = 'Alexa'));

update projects set creation_date = '2013-10-29' where name = 'ARA';
update projects set creation_date = '2007-11-19' where name = 'Kindle';
update projects set creation_date = '2014-11-06' where name = 'Alexa';

update developers d
set salary = 300
where salary = 0 and (select skill_id from developer_skills ds where ds.developer_id = d.id limit 1) <> 0;

update developers d
set salary = salary + 200
where d.id = (
select developer_id from developer_skills ds
join skills s on
d.id = ds.developer_id
and ds.skill_id = s.id
and s.branch = 'Java' and s.skill_level = 'Middle'
);


update developers d
set salary = salary + 250
where d.id = (
select developer_id from developer_skills ds
join skills s on ds.skill_id = s.id and ds.developer_id = d.id and s.branch = 'SQL'
);

update projects p
set cost = (
select sum(d.salary) from developers d
join project_developers pd on
d.id = pd.developer_id
and pd.project_id = p.id
);