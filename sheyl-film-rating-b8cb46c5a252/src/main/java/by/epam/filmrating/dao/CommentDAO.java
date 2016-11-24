package by.epam.filmrating.dao;

import by.epam.filmrating.entity.Comment;
import by.epam.filmrating.exception.DAOException;
import java.time.LocalDate;
import java.util.List;

/**
 * The {@code CommentDAO} class is participant in Data Access Object Pattern.
 * This class defines individual operations to be performed on a {@link Comment} objects.
 * @author Dmitry Rafalovich
 *
 */
public abstract class CommentDAO extends AbstractDAO<Comment> {

    @Override
    public void updateById(long id, Comment comment) throws DAOException {
        throw new DAOException("Unsupported operation.");
    }

    @Override
    public void deleteById(long id) throws DAOException {
        throw new DAOException("Unsupported operation.");
    }

    @Override
    public List<Comment> findAll() throws DAOException {
        throw new DAOException("Unsupported operation.");
    }

    /**
     * Ban comment with the {@code id} operation
     * @param id
     *        id of comment
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs;
     */
    public abstract void banComment(long id) throws DAOException;

    /**
     * Find all {@link Comment} objects of the film with {@code id}
     * in the database operation
     * @param id
     *        id of {@link by.epam.filmrating.entity.Film} object
     * @return {@link List} of comments
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs;
     */
    public abstract List<Comment> findCommentsByFilmId(long id) throws DAOException;

    /**
     * Find all {@link Comment} objects from user with {@code id}
     * in the database operation
     * @param id
     *        id of {@link by.epam.filmrating.entity.User} object
     * @return {@link List} of comments
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs;
     */
    public abstract List<Comment> findCommentsByUserId(long id) throws DAOException;

    /**
     * Find all {@link Comment} objects between {@code date1} and {@code date2}
     * @param date1
     *        start date
     * @param date2
     *        finish date
     * @return {@link List} of comments
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs;
     */
    public abstract List<Comment> findCommentsByDate(LocalDate date1, LocalDate date2) throws DAOException;
}
