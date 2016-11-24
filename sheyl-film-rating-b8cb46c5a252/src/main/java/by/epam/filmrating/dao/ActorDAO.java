package by.epam.filmrating.dao;

import by.epam.filmrating.entity.Actor;
import by.epam.filmrating.exception.DAOException;
import java.util.List;

/**
 * The {@code ActorDAO} class is participant in Data Access Object Pattern.
 * This class defines individual operations to be performed on a {@link Actor} objects.
 * @author Dmitry Rafalovich
 *
 */
public abstract class ActorDAO extends AbstractDAO<Actor> {

    /**
     * Find all {@link Actor} objects of the film with {@code id} in which they starred
     * in the database operation
     * @param id
     *        id of {@code Film} object
     * @return {@link List} of actors
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs;
     */
    public abstract List<Actor> findByFilmId(long id) throws DAOException;

    /**
     * Find actor id  by  {@code name} and {@code surname} operation
     * @param name
     *        actor name
     * @param surname
     *        actor surname
     * @return
     *        actor id or -1 if actor with this parameters not found
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs;
     */
    public abstract long findActorId(String name, String surname) throws DAOException;
}
