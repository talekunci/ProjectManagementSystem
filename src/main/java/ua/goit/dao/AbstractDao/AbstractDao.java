package ua.goit.dao.AbstractDao;

import ua.goit.dao.SqlExecutor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T extends Identity> implements Dao<T> {
    @Override
    public void delete(Long id) {
        String sqlQuery = String.format("delete from %s where id = ?", getTableName());
        SqlExecutor.execute(sqlQuery, ps -> ps.setLong(1, id));
    }

    @Override
    public Optional<T> get(Long id) {
        String sqlQuery = String.format("select * from %s where id = ?", getTableName());

        try (ResultSet resultSet = SqlExecutor.getResultSet(sqlQuery, ps -> ps.setLong(1, id))) {

            return resultSet.next() ? Optional.of(mapToEntity(resultSet)) : Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<T> getAll() {
        List<T> result = new ArrayList<>();
        String sqlQuery = String.format("select * from %s", getTableName());

        try (ResultSet resultSet = SqlExecutor.getResultSet(sqlQuery, ps -> {})) {

            while (resultSet.next()) {
                result.add(mapToEntity(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    protected abstract String getTableName();

    protected abstract T mapToEntity(ResultSet resultSet) throws SQLException;
}
