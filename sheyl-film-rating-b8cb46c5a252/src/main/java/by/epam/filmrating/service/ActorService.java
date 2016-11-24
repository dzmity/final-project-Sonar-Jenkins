package by.epam.filmrating.service;

import by.epam.filmrating.dao.ActorDAOImpl;
import by.epam.filmrating.entity.Actor;
import by.epam.filmrating.exception.DAOException;
import by.epam.filmrating.exception.ServiceException;
import java.util.List;

/**
 * The {@code ActorService} class represents a layer-class between
 * the {@code ActorDAOImpl} class (DAO class) and servlet.
 * @author Dmitry Rafalovich
 */
public class ActorService extends AbstractService<Actor> {

    /**
     * Gives the list of {@code Actor} objects from the database.
     * @return {@link List} of actors
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public List<Actor> findAll() throws ServiceException {

        try (ActorDAOImpl actorDAO = new ActorDAOImpl()) {
            return actorDAO.findAll();
        } catch (DAOException e) {
            throw new ServiceException("Exception in ActorService.", e);
        }
    }

    /**
     * Adds definite actor to the database.
     * @param actor
     *        {@code Actor} object
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public void create(Actor actor) throws ServiceException {

        try (ActorDAOImpl actorDAO = new ActorDAOImpl()) {
            actorDAO.create(actor);
        } catch (DAOException e) {
            throw new ServiceException("Exception in ActorService.", e);
        }
    }

    /**
     * Gives actor with specified {@code id} from the database.
     * @param id
     *        id of actor that must be found in the database
     * @return
     *        {@code Actor} object
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public Actor findById(long id) throws ServiceException {

        try (ActorDAOImpl actorDAO = new ActorDAOImpl()) {
            return actorDAO.findById(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception in ActorService.", e);
        }
    }

    /**
     * Updates actor with specified {@code id} in the database.
     * @param id
     *        id of actor that must be updated in the database
     * @param actor
     *        new actor that must be added to the database replaced
     *        the old by this {@code id}
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public void updateById(long id, Actor actor) throws ServiceException {

        try (ActorDAOImpl actorDAO = new ActorDAOImpl()) {
            actorDAO.updateById(id, actor);
        } catch (DAOException e) {
            throw new ServiceException("Exception in ActorService.", e);
        }
    }

    /**
     * Deletes definite actor from the database.
     * @param id
     *        id of actor that must be deleted from the database
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public void deleteById(long id) throws ServiceException {

        try (ActorDAOImpl actorDAO = new ActorDAOImpl()) {
            actorDAO.deleteById(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception in ActorService.", e);
        }
    }

    /**
     * Finds actor id  by {@code name} and {@code surname} in the database.
     * @param name
     *        actor name
     * @param surname
     *        actor surname
     * @return
     *        actor id or -1 if actor with this parameters not found
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    public long findActorId(String name, String surname) throws ServiceException {

        try (ActorDAOImpl actorDAO = new ActorDAOImpl()) {
            return actorDAO.findActorId(name.trim(), surname.trim());
        } catch (DAOException e) {
            throw new ServiceException("Exception in ActorService.", e);
        }
    }

}
