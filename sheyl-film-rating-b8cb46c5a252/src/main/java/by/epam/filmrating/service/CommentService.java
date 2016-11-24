package by.epam.filmrating.service;

import by.epam.filmrating.dao.CommentDAOImpl;
import by.epam.filmrating.entity.Comment;
import by.epam.filmrating.exception.DAOException;
import by.epam.filmrating.exception.ServiceException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

/**
 * The {@code CommentService} class represents a layer-class between
 * the {@code CommentDAOImpl} class (DAO class) and servlet.
 * @author Dmitry Rafalovich
 */
public class CommentService extends AbstractService<Comment> {

    /**
     * Gives the list of {@code Comment} objects from the database.
     * @return {@link List} of comments
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public List<Comment> findAll() throws ServiceException {
        try (CommentDAOImpl commentDAO = new CommentDAOImpl()) {
            return commentDAO.findAll();
        } catch (DAOException e) {
            throw new ServiceException("Exception in CommentService.", e);
        }
    }

    /**
     * Adds definite comment to the database.
     * @param comment
     *        {@code Comment} object
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public void create(Comment comment) throws ServiceException {
        try (CommentDAOImpl commentDAO = new CommentDAOImpl()) {
            commentDAO.create(comment);
        } catch (DAOException e) {
            throw new ServiceException("Exception in CommentService.", e);
        }
    }

    /**
     * Gives comment with specified {@code id} from the database.
     * @param id
     *        id of comment that must be found in the database
     * @return
     *        {@code Comment} object
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public Comment findById(long id) throws ServiceException {
        try (CommentDAOImpl commentDAO = new CommentDAOImpl()) {
            return commentDAO.findById(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception in CommentService.", e);
        }
    }

    /**
     * Updates comment with specified {@code id} in the database.
     * @param id
     *        id of comment that must be updated in the database
     * @param comment
     *        new comment that must be added to the database replaced
     *        the old by this {@code id}
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public void updateById(long id, Comment comment) throws ServiceException {
        try (CommentDAOImpl commentDAO = new CommentDAOImpl()) {
            commentDAO.updateById(id, comment);
        } catch (DAOException e) {
            throw new ServiceException("Exception in CommentService.", e);
        }
    }

    /**
     * Deletes definite comment from the database.
     * @param id
     *        id of comment that must be deleted from the database
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public void deleteById(long id) throws ServiceException {
        try (CommentDAOImpl commentDAO = new CommentDAOImpl()) {
            commentDAO.deleteById(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception in CommentService.", e);
        }
    }

    /**
     * Bans definite comment in the database.
     * @param id
     *        id of comment that must be baned in the database
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    public void banComment(long id) throws ServiceException {
        try (CommentDAOImpl commentDAO = new CommentDAOImpl()) {
            commentDAO.banComment(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception in CommentService.", e);
        }
    }

    /**
     * Gives the list of {@code Comment} objects sorted by the date
     * from the database written between {@code date1} and {@code date2}.
     * @param date1
     *        first date
     * @param date2
     *        last date
     * @return {@link List} of comments
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    public List<Comment> findCommentsByDate(LocalDate date1, LocalDate date2) throws ServiceException {
        try (CommentDAOImpl commentDAO = new CommentDAOImpl()) {
            List<Comment> comments = commentDAO.findCommentsByDate(date1, date2);
            Comparator<Comment> comp = Comparator.comparing(Comment::getDate).reversed();
            comments.sort(comp);
            return comments;
        } catch (DAOException e) {
            throw new ServiceException("Exception in CommentService.", e);
        }
    }
}
