package by.epam.filmrating.service;

import by.epam.filmrating.entity.Entity;
import by.epam.filmrating.exception.ServiceException;
import java.util.List;

/**
 * The {@code AbstractService} class defines the standard operations
 * to be performed on a model objects in the service-layer.
 * @author Dmitry Rafalovich
 */
abstract class AbstractService <T extends Entity> {

    /**
     * Adds definite model object to the database.
     * @param t
     *        model object that must be added to the database
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    public abstract void create(T t) throws ServiceException;

    /**
     * Gives model object with specified {@code id} from the database.
     * @param id
     *        id of model object that must be found in the database
     * @return
     *        model object
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    public abstract T findById(long id) throws ServiceException;

    /**
     * Updates model object with specified {@code id} in the database.
     * @param id
     *        id of model object that must be updated in the database
     * @param t
     *        new model object that must be added to the database replaced
     *        the old by this {@code id}
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    public abstract void updateById(long id, T t) throws ServiceException;

    /**
     * Deletes definite model object from the database.
     * @param id
     *        id of model object that must be deleted from the database
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    public abstract void deleteById(long id) throws ServiceException;

    /**
     * Gives the list of model objects from the database.
     * @return {@link List} of model objects
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    public abstract List<T> findAll() throws ServiceException;
}
