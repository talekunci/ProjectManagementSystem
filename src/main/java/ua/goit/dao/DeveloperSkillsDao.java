package ua.goit.dao;

import ua.goit.dao.AbstractDao.AbstractDao;
import ua.goit.model.Developer;
import ua.goit.model.Skill;
import ua.goit.model.DeveloperSkill;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class DeveloperSkillsDao extends AbstractDao<DeveloperSkill> {

    private final String sqlCreate = String.format("insert into %s(developer_id, skill_id) values(?, ?)", getTableName());

    private static DeveloperSkillsDao instance;

    private DeveloperSkillsDao() {
    }

    public static DeveloperSkillsDao getInstance() {
        return instance == null ? instance = new DeveloperSkillsDao() : instance;
    }

    @Override
    public Optional<DeveloperSkill> create(DeveloperSkill newEntity) {
        if (newEntity == null) return Optional.empty();

        SqlExecutor.execute(sqlCreate, ps -> {
            ps.setLong(1, newEntity.getDeveloper().getId());
            ps.setLong(2, newEntity.getSkill().getId());
        });
        return Optional.of(newEntity);
    }

    @Override
    public Optional<DeveloperSkill> createEntity(String sql) {
        String[] params = sql.split(" ");

        Optional<Developer> developer;
        Optional<Skill> skill;

        try {
            developer = DeveloperDao.getInstance().get(Long.parseLong(params[0]));
            skill = SkillDao.getInstance().get(Long.parseLong(params[1]));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }

        if (developer.isEmpty() || skill.isEmpty()) return Optional.empty();

        DeveloperSkill developerSkill = new DeveloperSkill(
                developer.get(),
                skill.get()
        );

        return Optional.of(developerSkill);
    }

    @Override
    protected String getTableName() {
        return "developer_skills";
    }

    @Override
    protected DeveloperSkill mapToEntity(ResultSet resultSet) throws SQLException {
        Optional<Developer> developer = DeveloperDao.getInstance().get(resultSet.getLong("developer_id"));
        Optional<Skill> skill = SkillDao.getInstance().get(resultSet.getLong("skill_id"));

        if (developer.isEmpty() || skill.isEmpty()) return null;

        return new DeveloperSkill(
                developer.get(),
                skill.get()
        );
    }

    @Override
    public int update(DeveloperSkill entity) {
        return 0;
    }
}
