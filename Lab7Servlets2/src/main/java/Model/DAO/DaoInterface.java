package Model.DAO;

import Model.Exception.DaoException;

import java.util.List;

/**
 * Interface, that all DAO classes should implement.
 *
 * @param <T> Type, that represents entity from table in database.
 */
public interface DaoInterface<T> {
    /**
     * Inserts new entity into database.
     * @param entity Entity to insert.
     * @throws DaoException
     */
    void create(T entity) throws DaoException;

    /**
     * Deletes entity from database by id.
     * @param id Identificator of entity to delete.
     * @throws DaoException
     */
    void delete(int id) throws DaoException;

    /**
     * Saves updated entity into database.
     * @param entity Entity to update.
     * @throws DaoException
     */
    void update(T entity) throws DaoException;

    /**
     * Gets entity by id.
     * @param id
     * @return
     */
    T getById(int id);

    /**
     * Gets all entities from table.
     * @return
     */
    List<T> getAll();
}
