package demo.bring.repository;

import java.util.List;

/**
 * Simple generalized contract for working with DB.
 *
 * @param <T> type of entity
 */
public interface EntityRepository<T> {

    T save(T entity);

    T delete (T entity);

    T deleteId(Long id);

    T getById(Long id);

    List<T> getAll();


}
