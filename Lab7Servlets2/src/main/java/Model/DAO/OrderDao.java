package Model.DAO;

import Model.Entity.*;
import Model.Entity.Order;
import Model.Exception.DaoException;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.List;

/**
 * DAO class for table order.
 */
public class OrderDao extends AbstractDao<Order> {
    private ClientDao clientDao;
    private TripDao tripDao;

    public OrderDao() {
        super();
        clientDao = new ClientDao();
        tripDao = new TripDao();
    }

    /**
     * Inserts new order into table.
     *
     * @param order Order to insert.
     * @throws DaoException
     */
    @Override
    public void create(Order order) throws DaoException {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            entityManager = emFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();

            insertOrder(order, entityManager);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new DaoException("Can't create order", e);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    private void insertOrder(Order order, EntityManager entityManager) {
        order.getClient().getOrders().add(order);
        order.getTrip().getOrders().add(order);

        entityManager.persist(order);
        //entityManager.merge(order);
        entityManager.merge(order.getClient());
        entityManager.merge(order.getTrip());
    }

    /**
     * Deletes order with specified id.
     *
     * @param id ID of order to delete.
     * @throws DaoException
     */
    @Override
    public void delete(int id) throws DaoException {
        delete(id, Order.class, "Can't delete order with ID = " + id);
    }

    /**
     * Updates order.
     *
     * @param order Order with id of order to update and new values of attributes.
     * @throws DaoException
     */
    @Override
    public void update(Order order) throws DaoException {
        update(order, "Can't update order");
    }

    /**
     * Retrieves order with specified id.
     *
     * @param id ID of order to retrieve.
     * @return Order with specified id.
     * @throws DaoException
     */
    @Override
    public Order getById(int id) {
        return getById(id, Order.class);
    }


    /**
     * Retrieves list of all orders from database.
     *
     * @return list of all orders
     * @throws DaoException
     */
    @Override
    public List<Order> getAll() {
        //return getAll("allOrders");
        return getAll(Order.class);
    }

    /**
     * Retrieves list of all orders of client.
     *
     * @param clientId ID of client whose orders to get.
     * @return List of all orders of client.
     * @throws DaoException
     */
    public List<Order> getOrdersOfClient(int clientId) {
        EntityManager entityManager = null;
        try {
            entityManager = emFactory.createEntityManager();
            Client client = clientDao.getById(clientId);
            return client.getOrders();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    /**
     * Creates new order. If after order client has more than ordersNumForDiscount paid orders,
     * ets discount for the client.
     * @param order Order to create.
     * @param ordersNumForDiscount Number of orders, that client needs to have to get discount.
     * @throws DaoException
     */
    public void addOrderWithSetDiscount(Order order, int ordersNumForDiscount) throws DaoException {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        try {
            entityManager = emFactory.createEntityManager();
            transaction = entityManager.getTransaction();
            transaction.begin();

            insertOrder(order, entityManager);
            if (countPaidOrdersOfClient(order.getClient().getClientId())+1 > ordersNumForDiscount) {
                order.getClient().setDiscount(20);
                order.getClient().setHasDiscount(true);
                entityManager.merge(order.getClient());
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new DaoException("Can't create new order", e);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }


    /**
     * Creates new order. If after order client has more than ordersNumForDiscount paid orders,
     * sets discount for the client.
     *
     * @param tripId               ID of trip for order.
     * @param clientId             ID of client that makes order.
     * @param isPaid               Shows, if order has already been paid for.
     * @param ordersNumForDiscount Number of orders, that client needs to have to get discount.
     * @throws DaoException
     */
    public void addOrderWithSetDiscount(int tripId, int clientId, boolean isPaid, int ordersNumForDiscount) throws DaoException {
        Order order = new Order();
        Client client = clientDao.getById(clientId);
        Trip trip = tripDao.getById(tripId);
        order.setClient(client);
        order.setTrip(trip);
        order.setPaid(isPaid);

        addOrderWithSetDiscount(order, ordersNumForDiscount);
    }

    /**
     * Creates new order. If after order client has more than ordersNumForDiscount paid orders,
     * sets discount for the client.
     * @param trip Trip for new order.
     * @param client Client for new order.
     * @param isPaid Shows, if order has already been paid for.
     * @param ordersNumForDiscount Number of orders, that client needs to have to get discount.
     * @throws DaoException
     */
    public void addOrderWithSetDiscount(Trip trip, Client client, boolean isPaid, int ordersNumForDiscount) throws DaoException {
        Order order = new Order();
        order.setClient(client);
        order.setTrip(trip);
        order.setPaid(isPaid);

        addOrderWithSetDiscount(order, ordersNumForDiscount);
    }

    /**
     * Counts paid orders of client.
     *
     * @param clientId ID of client whose orders to count.
     * @return Number of paid orders.
     * @throws DaoException
     */
    public int countPaidOrdersOfClient(int clientId) {
        long ordersNumber = 0;
        EntityManager entityManager = null;
        try {
            entityManager = emFactory.createEntityManager();
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            Root<Order> orderRoot = criteriaQuery.from(Order.class);
            //Join<Order, Client> orderClientJoin = orderRoot.join(Order_.client);

            Predicate isPaidPredicate = criteriaBuilder.equal(orderRoot.get(Order_.isPaid), true);
            //Predicate clientIdPredicate = criteriaBuilder.equal(orderClientJoin.get(Client_.clientId), clientId);
            Predicate clientIdPredicate = criteriaBuilder.equal(orderRoot.get(Order_.client).get(Client_.clientId), clientId);
            criteriaQuery.select(criteriaBuilder.count(orderRoot)).where(criteriaBuilder.and(isPaidPredicate, clientIdPredicate));

            TypedQuery<Long> typedQuery = entityManager.createQuery(criteriaQuery);
            ordersNumber = typedQuery.getSingleResult();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
        return (int)ordersNumber;
    }

}