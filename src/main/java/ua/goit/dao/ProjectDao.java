package ua.goit.dao;

import ua.goit.dao.AbstractDao.AbstractDao;
import ua.goit.model.Project;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ProjectDao extends AbstractDao<Project> {

    private final String sqlCreate = String.format("insert into %s(company_id, name, description, creation_date) values(?, ?, ?, ?)", getTableName());
    private final String sqlUpdate = String.format("update %s set company_id = ?, name = ?, description = ? where id = ?", getTableName());

    private static ProjectDao instance;

    private ProjectDao() {
    }

    public static ProjectDao getInstance() {
        return instance == null ? instance = new ProjectDao() : instance;
    }

    @Override
    public Optional<Project> create(Project newEntity) {
        if (newEntity == null) return Optional.empty();

        SqlExecutor.execute(sqlCreate, ps -> {
            ps.setLong(1, newEntity.getCompanyId());
            ps.setString(2, newEntity.getName());
            ps.setString(3, newEntity.getDescription());
            ps.setDate(4, newEntity.getCreationDate());
        });
        return Optional.of(newEntity);
    }

    @Override
    public Optional<Project> createEntity(String sql) {
        String[] params = sql.split(" ");

        Project project = new Project(Long.parseLong(params[0]), Long.parseLong(params[1]), params[2]);

        try {
            project.setDescription(params[3]);
            project.setCreationDate(new Date(Date.valueOf(params[4]).getTime()));
        } catch (ArrayIndexOutOfBoundsException ignore) {}

        return Optional.of(project);
    }

    @Override
    public int update(Project entity) {
        if (entity == null) return -1;

        return SqlExecutor.execute(sqlUpdate, ps -> {
            ps.setLong(5, entity.getId());
            ps.setLong(1, entity.getCompanyId());
            ps.setString(2, entity.getName());
            ps.setString(3, entity.getDescription());
            ps.setDate(4, entity.getCreationDate());
        });
    }

    @Override
    protected String getTableName() {
        return "projects";
    }

    @Override
    protected Project mapToEntity(ResultSet resultSet) throws SQLException {
        return new Project(
                resultSet.getLong("id"),
                resultSet.getLong("company_id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getDate("creation_date")
        );
    }
}
