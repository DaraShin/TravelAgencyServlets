package Model.DAO;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import Model.Entity.User;
import Model.Exception.DaoException;

public class UserDao {
	protected static final String FACTORY_NAME = "travelAgencyFactory";
    protected EntityManagerFactory emFactory;
    
    public UserDao() {
        emFactory = Persistence.createEntityManagerFactory(FACTORY_NAME);
    }

	public void create(User user) throws DaoException {
		EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            entityManager = emFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(user);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new DaoException("Can't create user", e);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
	}

	/*public void delete(int id) throws DaoException {
		delete(id, User.class, "Can't delete user");

	}

	public void update(User user) throws DaoException {
		update(user, "Can't update user");

	}*/

	public User getByLogin(String login) {
		EntityManager entityManager = null;
        try {
            entityManager = emFactory.createEntityManager();
            User userByLogin = entityManager.find(User.class, login);
            return userByLogin;
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
	}

}
