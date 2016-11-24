package by.epam.filmrating.dao;

import by.epam.filmrating.entity.Mark;
import by.epam.filmrating.exception.DAOException;
import java.util.List;

/**
 * The {@code MarkDAO} class is participant in Data Access Object Pattern.
 * This class defines individual operations to be performed on a {@link Mark} objects.
 * @author Dmitry Rafalovich
 *
 */
public abstract class MarkDAO extends AbstractDAO<Mark> {

    @Override
    public Mark findById(long id) throws DAOException {
        throw new DAOException("Unsupported operation.");
    }

    @Override
    public void updateById(long id, Mark mark) throws DAOException {
        throw new DAOException("Unsupported operation.");
    }

    @Override
    public void deleteById(long id) throws DAOException {
        throw new DAOException("Unsupported operation.");
    }

    @Override
    public List<Mark> findAll() throws DAOException {
        throw new DAOException("Unsupported operation.");
    }

    /**
     * Find film rating by film id operation
     * @param id
     *        film id
     * @return
     *        array with two members. 1 - (int) count of  marks putted the film
     *        2 - (double) average rating
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs;
     */
    public abstract Number[] findRatingByFilmId(long id) throws DAOException;

    /**
     * Find count of marks putted the films by user with {@code id}
     * @param id
     *        user id
     * @return count of marks from user
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs;
     */
    public abstract int findMarksCountByUserId(long id) throws DAOException;
}
