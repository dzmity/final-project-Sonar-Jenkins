package by.epam.filmrating.dao;

import by.epam.filmrating.entity.Genre;
import by.epam.filmrating.exception.DAOException;
import java.util.List;

/**
 * The {@code GenreDAO} class is participant in Data Access Object Pattern.
 * This class defines individual operations to be performed on a {@link Genre} objects.
 * @author Dmitry Rafalovich
 *
 */
public abstract class GenreDAO extends AbstractDAO<Genre>{

    @Override
    public Genre findById(long id) throws DAOException {
        throw new DAOException("Unsupported operation.");
    }

    /**
     * Find all {@link Genre} objects of the film with {@code id}  in the database operation
     * @param id
     *        id of {@link by.epam.filmrating.entity.Film} object
     * @return {@link List} of genres
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs;
     */
    public abstract List<Genre> findByFilmId(long id) throws DAOException;

    /**
     * Find genre id  by genre name operation
     * @param genre
     *        genre name
     * @return
     *        genre id or -1 if genre with this parameter not found
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs;
     */
    public abstract long findGenreId(String genre) throws DAOException;
}
