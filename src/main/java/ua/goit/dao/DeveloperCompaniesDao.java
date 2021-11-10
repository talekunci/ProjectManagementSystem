package ua.goit.dao;

import ua.goit.dao.AbstractDao.AbstractDao;
import ua.goit.model.Company;
import ua.goit.model.Developer;
import ua.goit.model.DeveloperCompany;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class DeveloperCompaniesDao extends AbstractDao<DeveloperCompany> {

    private final String sqlCreate = String.format("insert into %s(developer_id, company_id) values(?, ?)", getTableName());

    private static DeveloperCompaniesDao instance;

    private DeveloperCompaniesDao() {
    }

    public static DeveloperCompaniesDao getInstance() {
        return instance == null ? instance = new DeveloperCompaniesDao() : instance;
    }

    @Override
    public Optional<DeveloperCompany> create(DeveloperCompany newEntity) {
        if (newEntity == null) return Optional.empty();

        SqlExecutor.execute(sqlCreate, ps -> {
            ps.setLong(1, newEntity.getDeveloper().getId());
            ps.setLong(2, newEntity.getCompany().getId());
        });

        return Optional.of(newEntity);
    }

    @Override
    public Optional<DeveloperCompany> createEntity(String sql) {
        String[] params = sql.split(" ");

        Optional<Developer> developer;
        Optional<Company> company;

        try {
            developer = DeveloperDao.getInstance().get(Long.parseLong(params[0]));
            company = CompanyDao.getInstance().get(Long.parseLong(params[1]));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }

        if (developer.isEmpty() || company.isEmpty()) return Optional.empty();

        DeveloperCompany developerCompany = new DeveloperCompany(
                developer.get(),
                company.get()
        );

        return Optional.of(developerCompany);
    }

    @Override
    protected DeveloperCompany mapToEntity(ResultSet resultSet) throws SQLException {
        return null;
    }

    @Override
    public int update(DeveloperCompany entity) {
        return 0;
    }

    @Override
    protected String getTableName() {
        return "developer_companies";
    }
}
