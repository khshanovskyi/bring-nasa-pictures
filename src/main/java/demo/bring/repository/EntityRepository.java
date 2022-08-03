package demo.bring.repository;

import java.util.List;

public interface EntityRepository<T> {

    T save(T entity);

    T delete (T entity);

    T deleteId(Long id);

    T getById(Long id);

    List<T> getAll();


}
