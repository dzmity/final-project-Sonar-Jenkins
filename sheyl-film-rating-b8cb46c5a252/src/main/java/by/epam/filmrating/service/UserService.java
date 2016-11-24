package by.epam.filmrating.service;

import by.epam.filmrating.dao.CommentDAOImpl;
import by.epam.filmrating.dao.MarkDAOImpl;
import by.epam.filmrating.dao.UserDAOImpl;
import by.epam.filmrating.entity.Comment;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.DAOException;
import by.epam.filmrating.exception.ServiceException;
import by.epam.filmrating.util.ActionEvaluator;
import org.apache.commons.codec.digest.DigestUtils;
import java.util.Comparator;
import java.util.List;

/**
 * The {@code UserService} class represents a layer-class between
 * the {@code UserDAOImpl} class (DAO class) and servlet.
 * @author Dmitry Rafalovich
 */
public class UserService extends AbstractService<User> {

    /**
     * Gives the list of {@code User} objects from the database.
     * @return {@link List} of users
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public List<User> findAll() throws ServiceException {

        try (UserDAOImpl userDAO =  new UserDAOImpl();
             MarkDAOImpl markDAO = new MarkDAOImpl();
             CommentDAOImpl commentDAO = new CommentDAOImpl()) {
            List<User> users =  userDAO.findAll();
            for (User user : users) {
                user.setMarkCount(markDAO.findMarksCountByUserId(user.getId()));
                user.setComments(commentDAO.findCommentsByUserId(user.getId()));
            }
            Comparator<User> comp = Comparator.comparing(User::getRole).thenComparing(User::getLogin);
            users.sort(comp);
            return users;
        } catch (DAOException e) {
            throw new ServiceException("Exception in UserService.", e);
        }
    }

    /**
     * Adds definite user to the database.
     * @param user
     *        {@code User} object
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public void create(User user) throws ServiceException {

        try (UserDAOImpl userDAO =  new UserDAOImpl()) {
            String password = user.getPassword();
            user.setPassword(DigestUtils.md5Hex(password));
            userDAO.create(user);
        } catch (DAOException e) {
            throw new ServiceException("Exception in UserService.", e);
        }
    }

    /**
     * Gives user with specified {@code id} from the database.
     * @param id
     *        id of user that must be found in the database
     * @return
     *        {@code User} object
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public User findById(long id) throws ServiceException {

        try (UserDAOImpl userDAO =  new UserDAOImpl();
             MarkDAOImpl markDAO = new MarkDAOImpl();
             CommentDAOImpl commentDAO = new CommentDAOImpl()) {
            User user = userDAO.findById(id);
            user.setMarkCount(markDAO.findMarksCountByUserId(user.getId()));
            List<Comment> comments = commentDAO.findCommentsByUserId(user.getId());
            Comparator<Comment> comp = Comparator.comparing(Comment::getDate).reversed();
            comments.sort(comp);
            user.setComments(comments);
            return user;
        } catch (DAOException e) {
            throw new ServiceException("Exception in UserService.", e);
        }
    }

    /**
     * Updates user with specified {@code id} in the database.
     * @param id
     *        id of user that must be updated in the database
     * @param user
     *        new user that must be added to the database replaced
     *        the old by specified {@code id}
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public void updateById(long id, User user) throws ServiceException {
        try (UserDAOImpl userDAO =  new UserDAOImpl()) {
            userDAO.updateById(id, user);
        } catch (DAOException e) {
            throw new ServiceException("Exception in UserService.", e);
        }
    }

    /**
     * Deletes definite user from the database.
     * @param id
     *        id of user that must be deleted from the database
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    @Override
    public void deleteById(long id) throws ServiceException {

        try (UserDAOImpl userDAO =  new UserDAOImpl()) {
            userDAO.deleteById(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception in UserService.", e);
        }
    }

    /**
     * Finds user  by {@code login} and {@code password} in the database.
     * @param login
     *        user login
     * @param password
     *        user password
     * @return
     *        {@code User} object with this {@code id} or
     *        'empty' user if user not found by this parameters
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    public User findByLoginPassword(String login, String password) throws ServiceException {

        try (UserDAOImpl userDAO =  new UserDAOImpl()) {
           return userDAO.findByLoginPassword(login, DigestUtils.md5Hex(password));
        } catch (DAOException e) {
            throw new ServiceException("Exception in UserService.", e);
        }
    }

    /**
     * Checks ban datetime for all user in the database.
     * If ban datetime < current datetime - ban will be removed.
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    public void checkBanForAll() throws ServiceException {

        try (UserDAOImpl userDAO =  new UserDAOImpl()) {
            userDAO.checkBanForAll();
        } catch (DAOException e) {
            throw new ServiceException("Exception in UserService.", e);
        }
    }

    /**
     * Bans user with specified {@code id} for {@code days} days.
     * @param id
     *        user id
     * @param days
     *        count of ban days
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    public void banUserById(long id, int days) throws ServiceException {

        try (UserDAOImpl userDAO =  new UserDAOImpl()) {
            userDAO.banUserById(id, days);
            int amount = userDAO.findMarkById(id);
            amount += ActionEvaluator.BAN_USER;
            userDAO.updateMarkById(id, amount);
        } catch (DAOException e) {
            throw new ServiceException("Exception in UserService.", e);
        }
    }

    /**
     * Removes ban for user with specified {@code id}.
     * @param id
     *        user id
     * @throws ServiceException
     *         if a {@code DAOException} occurs.
     */
    public void liftBanUserById(long id) throws ServiceException {

        try (UserDAOImpl userDAO =  new UserDAOImpl()) {
            userDAO.liftBanUserById(id);
        } catch (DAOException e) {
            throw new ServiceException("Exception in UserService.", e);
        }
    }

    /**
     * Changes user rating for {@code mark} points in the database.
     * @param id
     *        user id
     * @param mark
     *        points
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    public void addMarkById(long id, int mark) throws ServiceException {

        try (UserDAOImpl userDAO =  new UserDAOImpl()) {
            int amount = userDAO.findMarkById(id);
            amount += mark;
            userDAO.updateMarkById(id, amount);
        } catch (DAOException e) {
            throw new ServiceException("Exception in UserService.", e);
        }
    }

    /**
     * Updates user rating in the database.
     * @param id
     *        user id
     * @param mark
     *        new rating
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    public void updatedMarkById(long id, int mark) throws ServiceException {

        try (UserDAOImpl userDAO =  new UserDAOImpl()) {
            userDAO.updateMarkById(id, mark);
        } catch (DAOException e) {
            throw new ServiceException("Exception in UserService.", e);
        }
    }

    /**
     *  Changes password for {@code User} object in the database.
     * @param id
     *        user id
     * @param oldPassword
     *        old user password
     * @param newPassword
     *        new user password
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    public void changePasswordById(long id, String oldPassword, String newPassword) throws ServiceException {

        try (UserDAOImpl userDAO =  new UserDAOImpl()) {
            userDAO.changePasswordById(id, DigestUtils.md5Hex(oldPassword), DigestUtils.md5Hex(newPassword));
        } catch (DAOException e) {
            throw new ServiceException("Exception in UserService.", e);
        }
    }

    /**
     * Changes avatar for {@code User} object in the database.
     * @param id
     *        user id
     * @param picturePath
     *        name of the new avatar
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    public void changePictureById(long id, String picturePath) throws ServiceException {

        try (UserDAOImpl userDAO =  new UserDAOImpl()) {
            userDAO.changePictureById(id, picturePath);
        } catch (DAOException e) {
            throw new ServiceException("Exception in UserService.", e);
        }
    }

    /**
     * Checks exist user with the specified {@code login} in the database.
     * @param login
     *        user login
     * @return true - if user exist otherwise - false
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    public boolean existLogin(String login) throws ServiceException {

        try (UserDAOImpl userDAO =  new UserDAOImpl()) {
           return userDAO.existLogin(login);
        } catch (DAOException e) {
            throw new ServiceException("Exception in UserService.", e);
        }
    }

    /**
     * Checks exist user with the specified {@code email} in the database.
     * @param email
     *        user email
     * @return true - if user exist otherwise - false
     * @throws ServiceException
     *        if a {@code DAOException} occurs.
     */
    public boolean existEmail(String email) throws ServiceException {

        try (UserDAOImpl userDAO =  new UserDAOImpl()) {
           return userDAO.existEmail(email);
        } catch (DAOException e) {
            throw new ServiceException("Exception in UserService.", e);
        }
    }
}
