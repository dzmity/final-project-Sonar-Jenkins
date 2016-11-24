package by.epam.filmrating.dao;

import by.epam.filmrating.entity.Entity;
import by.epam.filmrating.exception.DAOException;
import by.epam.filmrating.pool.ConnectionPool;
import by.epam.filmrating.pool.WrapperConnector;
import java.util.List;

/**
 * The {@code AbstractDAO} class is the base class in Data Access Object Pattern.
 * This class defines the standard operations to be performed on a model objects.
 * @author Dmitry Rafalovich
 *
 */
abstract class AbstractDAO <T extends Entity> implements AutoCloseable{

    protected WrapperConnector connector;

    /**
     * {@code AbstractDAO} constructor. Takes {@link java.sql.Connection} from {@link ConnectionPool}
     * which SQL statements from this class's methods will be executed
     */
    public AbstractDAO() {
        connector = ConnectionPool.getInstance().takeConnection();
    }

    /**
     * Add model object to the database operation
     * @param t
     *        Model object that must be added to the database
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs;
     */
    public abstract void create(T t) throws DAOException;

    /**
     * Find model object in the database operation
     * @param id
     *        id of model object that must be found in the database
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs;
     *        if model object not found;
     *        if this operation unsupported in specific DAO.
     */
    public abstract T findById(long id) throws DAOException;

    /**
     * Update model object in the database operation
     * @param id
     *        id of model object that must be updated in the database
     * @param t
     *        New model object that must be added to the database replaced
     *        the old by this {@code id}
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs;
     *        if this operation unsupported in specific DAO.
     */
    public abstract void updateById(long id, T t) throws DAOException;

    /**
     * Delete model object from the database operation
     * @param id
     *        id of model object that must be deleted from the database
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs;
     *        if this operation unsupported in specific DAO.
     */
    public abstract void deleteById(long id) throws DAOException;

    /**
     * Find all model objects in the database operation
     * @return {@link List} of model objects
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs;
     *        if this operation unsupported in specific DAO.
     */
    public abstract List<T> findAll() throws DAOException;

    /**
     * Returns {@link java.sql.Connection} in {@link ConnectionPool}
     */
    public void close() {
        connector.close();
    }
}
