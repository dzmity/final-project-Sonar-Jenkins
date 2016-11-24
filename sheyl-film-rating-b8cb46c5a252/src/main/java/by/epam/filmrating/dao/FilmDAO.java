package by.epam.filmrating.dao;

import by.epam.filmrating.entity.Film;
import by.epam.filmrating.exception.DAOException;
import java.util.List;

/**
 * The {@code FilmDAO} class is participant in Data Access Object Pattern.
 * This class defines individual operations to be performed on a {@link Film} objects.
 * @author Dmitry Rafalovich
 *
 */
public abstract class FilmDAO extends AbstractDAO<Film>{

    /**
     * Add record into the transition table 'film_actor' operation
     * @param filmId
     *        film id
     * @param actorId
     *        actor id
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs;
     */
    public abstract void addFilmActorById(long filmId, long actorId) throws DAOException;

    /**
     * Add record into the transition table 'film_genre' operation
     * @param filmId
     *        film id
     * @param genreId
     *        genre id
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs;
     */
    public abstract void addFilmGenreById(long filmId, long genreId) throws DAOException;

    /**
     * Delete record from the transition table 'film_actor' operation
     * @param id
     *        film id
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs;
     */
    public abstract void deleteFilmActorById(long id) throws DAOException;

    /**
     * Delete record from the transition table 'film_genre' operation
     * @param id
     *        film id
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs;
     */
    public abstract void deleteFilmGenreById(long id) throws DAOException;

    /**
     * Find {@link Film} objects in the database by {@code title} operation
     * @param title
     *        film title
     * @return {@link List} of films
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs.
     */
    public abstract List<Film> findFilmByTitle(String title) throws DAOException;

    /**
     * Find {@link Film} objects in the database by actor id operation
     * @param id
     *        actor id
     * @return {@link List} of films
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs;
     */
    public abstract List<Film> findFilmByActorId(long id) throws DAOException;

    /**
     * Find {@link Film} objects in the database by genre id operation
     * @param id
     *        genre id
     * @return {@link List} of films
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs;
     */
    public abstract List<Film> findFilmByGenreId(long id) throws DAOException;

    /**
     * Find {@link Film} objects in the database by director id operation
     * @param id
     *        director id
     * @return {@link List} of films
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs;
     */
    public abstract List<Film> findFilmByDirectorId(long id) throws DAOException;

    /**
     * Find {@link Film} objects in the database by {@code year} operation
     * @param year
     *        film year
     * @return {@link List} of films
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs;
     */
    public abstract List<Film> findFilmByYear(int year) throws DAOException;

    /**
     * Find film id in the database by {@code title} and director id operation
     * @param title
     *        film title
     * @param directorId
     *        director id
     * @return film id or -1 if film with this parameters not found
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs;
     */
    public abstract long findFilmId(String title, long directorId) throws DAOException;
}
