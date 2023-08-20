package Model.DAO;

import Model.Entity.Client;
import Model.Exception.DaoException;

import java.util.List;

/**
 * DAO class for table client.
 */
public class ClientDao extends AbstractDao<Client>{
    /**
     * Inserts new client into table.
     * @param client Client to insert.
     * @throws DaoException
     */
    @Override
    public void create(Client client) throws DaoException {
        create(client, "Can't create client");
    }

    /**
     * Deletes client with specified id.
     * @param id ID of client to delete.
     * @throws DaoException
     */
    @Override
    public void delete(int id) throws DaoException {
        delete(id, Client.class, "Can't delete client");
    }

    /**
     * Saves updated client into database.
     * @param client Client with id of client to update and new values of attributes.
     * @throws DaoException
     */
    @Override
    public void update(Client client) throws DaoException {
        update(client, "Can't update client");
    }

    /**
     * Retrieves client with specified id.
     * @param id ID of client to retrieve.
     * @return Client with specified id.
     * @throws DaoException
     */
    @Override
    public Client getById(int id) {
        return getById(id, Client.class);
    }

    /**
     * Retrieves list of all clients from database.
     * @return list of all clients
     * @throws DaoException
     */
    @Override
    public List<Client> getAll() {
        return getAll(Client.class);
    }
}
