package Model.DAO;

import Model.Entity.Client;
import Model.Exception.DaoException;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Superclass for all DAO classes.
 */
abstract public class AbstractDao<T> implements DaoInterface<T> {
    protected static final String FACTORY_NAME = "travelAgencyFactory";
    protected EntityManagerFactory emFactory;

    /**
     * Constructor without parameters.
     * Creates EntityManagerFactory.
     */
    public AbstractDao() {
        emFactory = Persistence.createEntityManagerFactory(FACTORY_NAME);
    }

    //abstract public void create(T entity) throws DaoException;

    /**
     * Inserts new entity into database.
     *
     * @param entity       Entity to insert.
     * @param errorMessage Message to show in case of error.
     * @throws DaoException
     */
    final protected void create(T entity, String errorMessage) throws DaoException {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            entityManager = emFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new DaoException(errorMessage, e);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    //abstract public void update(T entity) throws DaoException;

    /**
     * Updates info about entity in database.
     *
     * @param entity       Entity to update.
     * @param errorMessage Message to show in case of error.
     * @throws DaoException
     */
    final protected void update(T entity, String errorMessage) throws DaoException {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            entityManager = emFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new DaoException("Can't update client", e);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    //abstract public void delete(int id) throws DaoException;

    /**
     * Deletes entity from database by id.
     *
     * @param id            Identificator of entity to delete.
     * @param classToDelete Instance of class of entity to update.
     * @param errorMessage  Message to show in case of error.
     * @throws DaoException
     */
    final protected void delete(int id, Class<T> classToDelete, String errorMessage) throws DaoException {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            entityManager = emFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.remove(entityManager.find(classToDelete, id));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new DaoException(errorMessage, e);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    //abstract public T getById(int id);

    /**
     * Gets entity by id.
     *
     * @param id         Id of entity to find.
     * @param classToGet Instance of class of entity to find.
     * @return
     */
    final protected T getById(int id, Class<T> classToGet) {
        EntityManager entityManager = null;
        try {
            entityManager = emFactory.createEntityManager();
            T clientById = entityManager.find(classToGet, id);
            return clientById;
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    //abstract  public List<T> getAll();

    /**
     * Gets all entities from table.
     *
     * @param queryName Name of query to select all entities.
     * @return
     */
    final protected List<T> getAll(Class<T> sourceClass) {
        EntityManager entityManager = null;
        try {
            entityManager = emFactory.createEntityManager();
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(sourceClass);
            Root<T> root = criteriaQuery.from(sourceClass);
            criteriaQuery.select(root);

            TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);
            List<T> allEntities = typedQuery.getResultList();
            return allEntities;
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }
}
