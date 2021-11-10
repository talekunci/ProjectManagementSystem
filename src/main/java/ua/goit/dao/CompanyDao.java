package ua.goit.dao;

import ua.goit.dao.AbstractDao.AbstractDao;
import ua.goit.model.Company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CompanyDao extends AbstractDao<Company> {

    private final String sqlCreate = String.format("insert into %s(name, description) values(?, ?)", getTableName());
    private final String sqlUpdate = String.format("update %s set name = ?, description = ? where id = ?", getTableName());

    private static CompanyDao instance;

    private CompanyDao() {
    }

    public static CompanyDao getInstance() {
        return instance == null ? instance = new CompanyDao() : instance;
    }

    @Override
    public Optional<Company> create(Company newEntity) {
        if (newEntity == null) return Optional.empty();

        SqlExecutor.execute(sqlCreate, ps -> {
            ps.setString(1, newEntity.getName());
            ps.setString(2, newEntity.getDescription());
        });
        return Optional.of(newEntity);
    }

    @Override
    public Optional<Company> createEntity(String sql) {
        String[] params = sql.split(" ");

        Company company = new Company(Long.parseLong(params[0]), params[1]);

        try {
            company.setDescription(params[2]);
        } catch (ArrayIndexOutOfBoundsException ignore) {}

        return Optional.of(company);
    }

    @Override
    public int update(Company entity) {
        if (entity == null) return -1;

        return SqlExecutor.execute(sqlUpdate, ps -> {
            ps.setLong(3, entity.getId());
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getDescription());
        });
    }

    @Override
    protected String getTableName() {
        return "companies";
    }

    @Override
    protected Company mapToEntity(ResultSet resultSet) throws SQLException {
        return new Company(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("description")
        );
    }
}
