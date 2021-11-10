package ua.goit.dao;

import ua.goit.dao.AbstractDao.AbstractDao;
import ua.goit.model.Developer;
import ua.goit.model.Project;
import ua.goit.model.ProjectDeveloper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ProjectDevelopersDao extends AbstractDao<ProjectDeveloper> {

    private final String sqlCreate = String.format("insert into %s(developer_id, project_id) values(?, ?)", getTableName());

    private static ProjectDevelopersDao instance;

    private ProjectDevelopersDao() {
    }

    public static ProjectDevelopersDao getInstance() {
        return instance == null ? instance = new ProjectDevelopersDao() : instance;
    }

    @Override
    public Optional<ProjectDeveloper> create(ProjectDeveloper newEntity) {
        if (newEntity == null) return Optional.empty();

        SqlExecutor.execute(sqlCreate, ps -> {
            ps.setLong(1, newEntity.getDeveloper().getId());
            ps.setLong(2, newEntity.getProject().getId());
        });
        return Optional.of(newEntity);
    }

    @Override
    public Optional<ProjectDeveloper> createEntity(String sql) {
        String[] params = sql.split(" ");

        Optional<Developer> developer;
        Optional<Project> project;

        try {
            developer = DeveloperDao.getInstance().get(Long.parseLong(params[0]));
            project = ProjectDao.getInstance().get(Long.parseLong(params[1]));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }

        if (developer.isEmpty() || project.isEmpty()) return Optional.empty();

        ProjectDeveloper projectDeveloper = new ProjectDeveloper(
                developer.get(),
                project.get()
        );

        return Optional.of(projectDeveloper);
    }

    @Override
    protected String getTableName() {
        return "project_developers";
    }

    @Override
    protected ProjectDeveloper mapToEntity(ResultSet resultSet) throws SQLException {
        Optional<Developer> developer = DeveloperDao.getInstance().get(resultSet.getLong("developer_id"));
        Optional<Project> project = ProjectDao.getInstance().get(resultSet.getLong("project_id"));

        if (developer.isEmpty() || project.isEmpty()) return null;

        return new ProjectDeveloper(
                developer.get(),
                project.get()
        );
    }

    @Override
    public int update(ProjectDeveloper entity) {
        return 0;
    }
}
