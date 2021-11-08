package ua.goit.dao;

import ua.goit.dao.AbstractDao.AbstractDao;
import ua.goit.model.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CustomerDao extends AbstractDao<Customer> {

    private final String sqlCreate = String.format("insert into %s(name, description) values(?, ?)", getTableName());
    private final String sqlUpdate = String.format("update %s set name = ?, description = ? where id = ?", getTableName());

    private static CustomerDao instance;

    private CustomerDao() {
    }

    public static CustomerDao getInstance() {
        return instance == null ? instance = new CustomerDao() : instance;
    }

    @Override
    public Optional<Customer> create(Customer newEntity) {
        SqlExecutor.execute(sqlCreate, ps -> {
            ps.setString(1, newEntity.getName());
            ps.setString(2, newEntity.getDescription());
        });

        return Optional.of(newEntity);
    }

    @Override
    public Optional<Customer> createEntity(String sql) {
        String[] params = sql.split(" ");

        Customer customer = new Customer(0L, params[1]);

        try {
            customer.setDescription(params[2]);
        } catch (ArrayIndexOutOfBoundsException ignore) {}


        return Optional.of(customer);
    }

    @Override
    public int update(Customer entity) {
        return SqlExecutor.execute(sqlUpdate, ps -> {
            ps.setLong(3, entity.getId());
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getDescription());
        });
    }

    @Override
    protected String getTableName() {
        return "customers";
    }

    @Override
    protected Customer mapToEntity(ResultSet resultSet) throws SQLException {
        return new Customer(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("description")
        );
    }
}
