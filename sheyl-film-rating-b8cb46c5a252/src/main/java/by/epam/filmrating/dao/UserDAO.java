package by.epam.filmrating.dao;

import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.DAOException;

/**
 * The {@code UserDAO} class is participant in Data Access Object Pattern.
 * This class defines individual operations to be performed on a {@link User} objects.
 * @author Dmitry Rafalovich
 *
 */
public abstract class UserDAO extends AbstractDAO<User> {
    /**
     * Find {@link User} object by user name and user surname operation.
     * @param login
     *        user login
     * @param password
     *        user password
     * @return user with this {@code id} or 'empty' user if user not found by this parameters
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs.
     */
    public abstract User findByLoginPassword(String login, String password) throws DAOException;

    /**
     * Ban user with {@code id} for {@code days} days operation.
     * @param id
     *        user id
     * @param days
     *        count of ban days
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs.
     */
    public abstract void banUserById(long id, int days) throws DAOException;

    /**
     * Remove ban for user with {@code id} operation.
     * @param id
     *        user id
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs.
     */
    public abstract void liftBanUserById(long id) throws DAOException;

    /**
     * Update user rating operation.
     * @param id
     *        user id
     * @param mark
     *        new rating
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs.
     */
    public abstract void updateMarkById(long id, int mark) throws DAOException;

    /**
     *  Find user rating by id operation.
     * @param id
     *        user id
     * @return user rating
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs.
     */
    public abstract int findMarkById(long id) throws DAOException;

    /**
     *  Change user password operation.
     * @param id
     *        user id
     * @param oldPassword
     *        old user password
     * @param newPassword
     *        new user password
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs.
     */
    public abstract void changePasswordById(long id, String oldPassword, String newPassword) throws DAOException;

    /**
     * Change user avatar operation.
     * @param id
     *        user id
     * @param picturePath
     *        name of the new avatar
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs.
     */
    public abstract void changePictureById(long id, String picturePath) throws DAOException;

    /**
     * Method checks exist user with the specified login.
     * @param login
     *        user login
     * @return true - if user exist otherwise - false
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs.
     */
    public abstract boolean existLogin(String login) throws DAOException;

    /**
     * Method checks exist user with the specified email.
     * @param email
     *        user email
     * @return true - if user exist otherwise - false
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs.
     */
    public abstract boolean existEmail(String email) throws DAOException;

    /**
     * Method check ban datetime for all user in the database.
     * If ban datetime < current datetime - ban will be removed.
     * @throws DAOException
     *        if a {@link java.sql.SQLException} occurs.
     */
    public abstract void checkBanForAll() throws DAOException;
}
