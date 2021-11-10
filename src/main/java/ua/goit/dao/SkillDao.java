package ua.goit.dao;

import ua.goit.dao.AbstractDao.AbstractDao;
import ua.goit.model.Skill;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class SkillDao extends AbstractDao<Skill> {

    private final String sqlCreate = String.format("insert into %s(branch, skill_level) values(?, ?)", getTableName());
    private final String sqlUpdate = String.format("update %s set branch = ?, skill_level = ? where id = ?", getTableName());

    private static SkillDao instance;

    private SkillDao() {
    }

    public static SkillDao getInstance() {
        return instance == null ? instance = new SkillDao() : instance;
    }

    @Override
    public Optional<Skill> create(Skill newEntity) {
        if (newEntity == null) return Optional.empty();

        SqlExecutor.execute(sqlCreate, ps -> {
            ps.setString(1, newEntity.getBranch());
            ps.setString(2, newEntity.getSkillLevel());
        });

        return Optional.of(newEntity);
    }

    @Override
    public Optional<Skill> createEntity(String sql) {
        String[] params = sql.split(" ");

        return Optional.of(new Skill(Long.parseLong(params[0]), params[1], params[2]));
    }

    @Override
    public int update(Skill entity) {
        if (entity == null) return -1;

        return SqlExecutor.execute(sqlUpdate, ps -> {
            ps.setLong(3, entity.getId());
            ps.setString(1, entity.getBranch());
            ps.setString(2, entity.getSkillLevel());
        });
    }

    @Override
    protected String getTableName() {
        return "skills";
    }

    @Override
    protected Skill mapToEntity(ResultSet resultSet) throws SQLException {
        return new Skill(
                resultSet.getLong("id"),
                resultSet.getString("branch"),
                resultSet.getString("skill_level")
        );
    }
}
