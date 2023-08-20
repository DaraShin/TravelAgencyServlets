package Model.DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import Model.Entity.Discount;
import Model.Entity.User;
import Model.Exception.DaoException;

public class DiscountDao  {
    protected static final String FACTORY_NAME = "travelAgencyFactory";
    protected EntityManagerFactory emFactory;

    /**
     * Constructor without parameters.
     * Creates EntityManagerFactory.
     */
    public DiscountDao() {
        emFactory = Persistence.createEntityManagerFactory(FACTORY_NAME);
    }
    
	public Discount getDiscount() {
		EntityManager entityManager = null;
        try {
            entityManager = emFactory.createEntityManager();
            Discount discount = entityManager.find(Discount.class, 1);
            return discount;
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
	}
	
	public void setDiscount(int discount) throws DaoException {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            entityManager = emFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();
            Discount discountEntity = entityManager.find(Discount.class, 1);
            discountEntity.setDiscountValue(discount);
            entityManager.merge(discountEntity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new DaoException("Can't update discount", e);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
	}

}
