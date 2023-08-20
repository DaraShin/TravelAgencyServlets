package Model.DAO;

import Model.Entity.Trip;
import Model.Entity.Trip_;
import Model.Exception.DaoException;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * DAO class for table trip.
 */
public class TripDao extends AbstractDao<Trip> {
    /**
     * Inserts new trip into table.
     *
     * @param trip Trip to insert.
     * @throws DaoException
     */
    @Override
    public void create(Trip trip) throws DaoException {
        create(trip, "Can't create trip");
    }

    /**
     * Deletes trip with specified id.
     *
     * @param id ID of trip to delete.
     * @throws DaoException
     */
    @Override
    public void delete(int id) throws DaoException {
        delete(id, Trip.class, "Can't delete trip with ID = " + id);
    }

    /**
     * Updates trip.
     *
     * @param trip Trip with id of trip to update and new values of attributes.
     * @throws DaoException
     */
    @Override
    public void update(Trip trip) throws DaoException {
        update(trip, "Can't update trip");
    }

    /**
     * Retrieves trip with specified id.
     *
     * @param id ID of trip to retrieve.
     * @return Trip with specified id.
     * @throws DaoException
     */
    @Override
    public Trip getById(int id) {
        return getById(id, Trip.class);
    }

    /**
     * Retrieves list of all trips from database.
     *
     * @return list of all trips
     * @throws DaoException
     */
    @Override
    public List<Trip> getAll() {
        return getAll(Trip.class);
    }

    /**
     * Retrieves list of all hot tours of specified type.
     *
     * @param tripType Type of trip to get info about.
     * @return list of all hot tours of specified type.
     * @throws DaoException
     */
    public List<Trip> getHotToursOfSpecifiedType(String tripType) {
        EntityManager entityManager = null;
        List<Trip> tripList;
        try {
            entityManager = emFactory.createEntityManager();
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Trip> criteriaQuery = criteriaBuilder.createQuery(Trip.class);
            Root<Trip> rootTrip = criteriaQuery.from(Trip.class);

            Predicate tripTypePredicate = criteriaBuilder.equal(rootTrip.get(Trip_.tripType), tripType.trim().toLowerCase());
            Predicate isHotTourPredicate = criteriaBuilder.equal(rootTrip.get(Trip_.isHotTour),true);
            criteriaQuery.select(rootTrip).where(criteriaBuilder.and(tripTypePredicate, isHotTourPredicate));
            TypedQuery<Trip> typedQuery = entityManager.createQuery(criteriaQuery);
            List<Trip> trips = typedQuery.getResultList();
            return trips;
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }
}