package ua.goit.dao.AbstractDao;

import java.util.List;
import java.util.Optional;

public interface Dao<T extends Identity> {

    List<T> getAll();
    Optional<T> get(Long id);
    Optional<T> create(T newEntity);
    Optional<T> createEntity(String sql);
    int update(T entity);
    void delete(Long id);

}
