package ua.goit.dao;

import ua.goit.dao.AbstractDao.AbstractDao;
import ua.goit.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CustomerProjectsDao extends AbstractDao<CustomerProject> {

    private final String sqlCreate = String.format("insert into %s(customer_id, project_id) values(?, ?)", getTableName());

    private static CustomerProjectsDao instance;

    private CustomerProjectsDao() {
    }

    public static CustomerProjectsDao getInstance() {
        return instance == null ? instance = new CustomerProjectsDao() : instance;
    }

    @Override
    public Optional<CustomerProject> create(CustomerProject newEntity) {
        if (newEntity == null) return Optional.empty();

        SqlExecutor.execute(sqlCreate, ps -> {
            ps.setLong(1, newEntity.getCustomer().getId());
            ps.setLong(2, newEntity.getProject().getId());
        });

        return Optional.of(newEntity);
    }

    @Override
    public Optional<CustomerProject> createEntity(String sql) {
        String[] params = sql.split(" ");

        Optional<Customer> customer;
        Optional<Project> project;

        try {
            customer = CustomerDao.getInstance().get(Long.parseLong(params[0]));
            project = ProjectDao.getInstance().get(Long.parseLong(params[1]));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }

        if (customer.isEmpty() || project.isEmpty()) return Optional.empty();

        CustomerProject customerProject = new CustomerProject(
                customer.get(),
                project.get()
        );

        return Optional.of(customerProject);
    }

    @Override
    protected CustomerProject mapToEntity(ResultSet resultSet) throws SQLException {
        Optional<Customer> customer;
        Optional<Project> project;

        try {
            customer = CustomerDao.getInstance().get(resultSet.getLong("customer_id"));
            project = ProjectDao.getInstance().get(resultSet.getLong("project_id"));
        } catch (NumberFormatException e) {
            return null;
        }

        if (customer.isEmpty() || project.isEmpty()) return null;

        return new CustomerProject(customer.get(), project.get());
    }

    @Override
    protected String getTableName() {
        return "customers_projects";
    }

    @Override
    public int update(CustomerProject entity) {
        return 0;
    }
}
