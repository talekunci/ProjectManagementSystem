package ua.goit.dao;

import ua.goit.dao.AbstractDao.AbstractDao;
import ua.goit.model.Developer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeveloperDao extends AbstractDao<Developer> {

    private final String sqlCreate = String.format("insert into %s(name, age, gender, description, salary) values(?, ?, ?, ?, ?)", getTableName());
    private final String sqlUpdate = String.format("update %s set name = ?, age = ?, gender = ?, description = ?, salary = ? where id = ?", getTableName());

    private static DeveloperDao instance;

    private DeveloperDao() {
    }

    public static DeveloperDao getInstance() {
        return instance == null ? instance = new DeveloperDao() : instance;
    }

    public List<Developer> getDevelopersByProjectId(Long projectId) {
        String sqlGetDevelopers = "select * from developers d join project_developers pd on pd.project_id = ? and pd.developer_id = d.id";
        List<Developer> result = new ArrayList<>();

        try (ResultSet resultSet = SqlExecutor.getResultSet(sqlGetDevelopers, ps -> ps.setLong(1, projectId))) {

            while (resultSet.next()) {
                result.add(mapToEntity(resultSet));
            }

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        }
    }

    public List<Developer> getDevelopersBySkill(Long... skillsId) {
        String sqlGetDevelopers = "select * from developers d join developer_skills ds on ds.skill_id = ? and ds.developer_id = d.id";
        List<Developer> result = new ArrayList<>();

        for (Long id : skillsId) {
            try (ResultSet resultSet = SqlExecutor.getResultSet(sqlGetDevelopers, ps -> ps.setLong(1, id))) {

                while (resultSet.next()) {
                    result.add(mapToEntity(resultSet));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    @Override
    public Optional<Developer> create(Developer newEntity) {
        SqlExecutor.execute(sqlCreate, ps -> {
            ps.setString(1, newEntity.getName());
            ps.setInt(2, newEntity.getAge());
            ps.setString(3, newEntity.getGender());
            ps.setString(4, newEntity.getDescription());
            ps.setInt(5, newEntity.getSalary());
        });

        return Optional.of(newEntity);
    }

    @Override
    public Optional<Developer> createEntity(String sql) {
        String[] params = sql.split(" ");

        Developer dev = new Developer(Long.parseLong(params[0]), params[1]);

        try {
            dev.setAge(Integer.parseInt(params[2]));
            dev.setGender(params[3]);
            dev.setDescription(params[4]);
            dev.setSalary(Integer.parseInt(params[5]));
        } catch (ArrayIndexOutOfBoundsException
                | NumberFormatException ignored) {}

        return Optional.of(dev);
    }

    @Override
    public int update(Developer entity) {
        return SqlExecutor.execute(sqlUpdate, ps -> {
            ps.setLong(6, entity.getId());
            ps.setString(1, entity.getName());
            ps.setInt(2, entity.getAge());
            ps.setString(3, entity.getGender());
            ps.setString(4, entity.getDescription());
            ps.setInt(5, entity.getSalary());
        });
    }

    @Override
    protected String getTableName() {
        return "developers";
    }

    @Override
    protected Developer mapToEntity(ResultSet resultSet) throws SQLException {
        return new Developer(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getInt("age"),
                resultSet.getString("gender"),
                resultSet.getString("description"),
                resultSet.getInt("salary")
        );
    }
}
