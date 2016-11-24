package by.epam.filmrating.dao;

import by.epam.filmrating.entity.Director;
import by.epam.filmrating.exception.DAOException;

/**
 * The {@code DirectorDAO} class is participant in Data Access Object Pattern.
 * This class defines individual operations to be performed on a {@link Director} objects.
 * @author Dmitry Rafalovich
 *
 */
public abstract class DirectorDAO extends AbstractDAO<Director> {

    /**
     * Find director id  by  {@code name} and {@code surname} operation
     * @param name
     *        director name
     * @param surname
     *        director surname
     * @return
     *        director id or -1 if director with this parameters not found
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs;
     */
    public abstract long findDirectorId(String name, String surname) throws DAOException;
}
