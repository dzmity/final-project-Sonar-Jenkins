package by.epam.filmrating.dao;

import by.epam.filmrating.entity.Role;
import by.epam.filmrating.entity.Status;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.DAOException;
import by.epam.filmrating.util.ActionEvaluator;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code UserDAOImpl} class is participant in Data Access Object Pattern.
 * This class is responsible to get {@link User} objects from database.
 * In this class all the inherited methods are implemented.
 * @author Dmitry Rafalovich
 *
 */
public class UserDAOImpl extends UserDAO {

    private static final String USER_ID = "user_id";
    private static final String LOGIN = "login";
    private static final String BAN_DATE = "ban_date";
    private static final String FIRST_NAME = "name";
    private static final String LAST_NAME = "surname";
    private static final String EMAIL = "email";
    private static final String PICTURE_PATH = "picturePath";
    private static final String ROLE = "role";
    private static final String RATING = "rating";

    private static final String SQL_FIND_ALL = "SELECT user.user_id, user.login, user.name, user.surname, user.email, " +
            " user.role, user.rating, user.picturePath, user.ban_date FROM database1.user ";

    private static final String SQL_ADD_USER = "INSERT INTO database1.user (login, password, name, surname," +
            "email) VALUES (?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE_USER = "UPDATE database1.user SET name = ?, surname = ?, login = ? " +
            "WHERE user_id = ?";

    private static final String SQL_DELETE_USER = "DELETE FROM database1.user WHERE user_id = ?";

    private static final String SQL_FIND_USER_BY_LOGIN_PASSWORD = "SELECT  user.user_id, user.login, user.name, user.surname, user.email, " +
            " user.role, user.rating, user.picturePath, user.ban_date FROM database1.user WHERE login = ? AND password = ?";

    private static final String SQL_FIND_USER_BY_ID = "SELECT  user.user_id, user.login, user.name, user.surname, user.email, " +
            " user.role, user.rating, user.picturePath, user.ban_date FROM database1.user WHERE user_id = ?";

    private static final String SQL_BAN_USER_BY_ID = "UPDATE database1.user SET ban_date = ? WHERE user_id = ?";
    private static final String SQL_LIFT_BAN_USER_BY_ID = "UPDATE database1.user SET ban_date = ? WHERE user_id = ?";
    private static final String SQL_CHECK_BAN = "UPDATE database1.user SET ban_date = ? WHERE ban_date < NOW()";
    private static final String SQL_UPDATE_MARK_BY_ID = "UPDATE database1.user SET rating = ? WHERE user_id = ?";
    private static final String SQL_FIND_MARK_BY_ID = "SELECT rating FROM database1.user WHERE user_id = ?";
    private static final String SQL_CHANGE_PASSWORD_BY_ID = "UPDATE database1.user SET password = ? WHERE user_id = ? AND password = ?";
    private static final String SQL_CHANGE_PICTURE_BY_ID = "UPDATE database1.user SET picturePath = ? WHERE user_id = ?";
    private static final String SQL_EXIST_LOGIN = "SELECT * FROM database1.user WHERE login = ?";
    private static final String SQL_EXIST_EMAIL = "SELECT * FROM database1.user WHERE email = ?";

    @Override
    public List<User> findAll() throws DAOException{

        List<User> users = new ArrayList<>();
        try (PreparedStatement statement = connector.prepareStatement(SQL_FIND_ALL);
             ResultSet set = statement.executeQuery()) {

            while (set.next()) {
                User user = new User();
                user.setId(set.getInt(USER_ID));
                user.setLogin(set.getString(LOGIN));
                user.setName(set.getString(FIRST_NAME));
                user.setSurname(set.getString(LAST_NAME));
                user.setEmail(set.getString(EMAIL));
                Role role = Role.valueOf(set.getString(ROLE).toUpperCase());
                user.setRole(role);
                int mark = set.getInt(RATING);
                if (mark > ActionEvaluator.EXPERIENCED_MAX_SUM) {
                    user.setStatus(Status.MASTER);
                } else if (mark > ActionEvaluator.BEGINNER_MAX_SUM) {
                    user.setStatus(Status.EXPERIENCED);
                } else {
                    user.setStatus(Status.BEGINNER);
                }
                users.add(user);
            }
            return users;

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during user finding", e);
        }
    }

    @Override
    public void create(User user) throws DAOException{

        try (PreparedStatement statement = connector.prepareStatement(SQL_ADD_USER)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setString(4, user.getSurname());
            statement.setString(5, user.getEmail());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during user adding", e);
        }
    }

    @Override
    public User findById(long id) throws DAOException{

        User user;
        try (PreparedStatement statement = connector.prepareStatement(SQL_FIND_USER_BY_ID)) {
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            user = buildUser(set);
            return user;
        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during user finding by login & password", e);
        }
    }

     @Override
    public void updateById(long id, User user) throws DAOException {

         try (PreparedStatement statement = connector.prepareStatement(SQL_UPDATE_USER)) {

             statement.setString(1, user.getName());
             statement.setString(2, user.getSurname());
             statement.setString(3, user.getLogin());
             statement.setLong(4, id);
             statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during user updating", e);
        }
    }

    @Override
    public void deleteById(long id) throws DAOException {

        try (PreparedStatement statement = connector.prepareStatement(SQL_DELETE_USER)) {
            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during user deleting", e);
        }
    }

    @Override
    public User findByLoginPassword(String login, String password) throws DAOException{

        User user;
        try (PreparedStatement statement = connector.prepareStatement(SQL_FIND_USER_BY_LOGIN_PASSWORD)) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet set = statement.executeQuery();
            user = buildUser(set);
            return user;
        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during user finding by login & password", e);
        }
    }

    @Override
    public void banUserById(long id, int days) throws DAOException {
        try (PreparedStatement statement = connector.prepareStatement(SQL_BAN_USER_BY_ID)) {

            LocalDateTime dateTime = LocalDateTime.now(ZoneId.systemDefault()).plusDays(days);
            Timestamp timestamp = Timestamp.valueOf(dateTime);
            statement.setTimestamp(1, timestamp);
            statement.setLong(2, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during user banning", e);
        }
    }

    @Override
    public void checkBanForAll() throws DAOException {
        try (PreparedStatement statement = connector.prepareStatement(SQL_CHECK_BAN)) {
            statement.setDate(1, null);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during user ban checking", e);
        }
    }

    @Override
    public void liftBanUserById(long id) throws DAOException {
        try (PreparedStatement statement = connector.prepareStatement(SQL_LIFT_BAN_USER_BY_ID)) {
             statement.setDate(1, null);
             statement.setLong(2, id);
             statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during user banning", e);
        }
    }

    @Override
    public void updateMarkById(long id, int mark) throws DAOException {
        try (PreparedStatement statement = connector.prepareStatement(SQL_UPDATE_MARK_BY_ID)) {

             statement.setInt(1, mark);
             statement.setLong(2, id);
             statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during user updating mark", e);
        }
    }

    @Override
    public int findMarkById(long id) throws DAOException {

        try (PreparedStatement statement = connector.prepareStatement(SQL_FIND_MARK_BY_ID)) {
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            set.next();
            return set.getInt(RATING);

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during mark finding", e);
        }
    }

    @Override
    public void changePasswordById(long id, String oldPassword, String newPassword) throws DAOException {

        try (PreparedStatement statement = connector.prepareStatement(SQL_CHANGE_PASSWORD_BY_ID)) {

             statement.setString(1, newPassword);
             statement.setLong(2, id);
             statement.setString(3, oldPassword);
             statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during user changing password", e);
        }
    }

    @Override
    public void changePictureById(long id, String picturePath) throws DAOException {

        try (PreparedStatement statement = connector.prepareStatement(SQL_CHANGE_PICTURE_BY_ID)) {

             statement.setString(1, picturePath);
             statement.setLong(2, id);
             statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during user changing picture", e);
        }
    }

    @Override
    public boolean existLogin(String login) throws DAOException {

        try (PreparedStatement statement = connector.prepareStatement(SQL_EXIST_LOGIN)) {
            statement.setString(1, login);
            ResultSet set = statement.executeQuery();
            return set.next();

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during user checking login exist", e);
        }
    }

    @Override
    public boolean existEmail(String email) throws DAOException {

        try (PreparedStatement statement = connector.prepareStatement(SQL_EXIST_EMAIL)) {
            statement.setString(1, email);
            ResultSet set = statement.executeQuery();
            return set.next();

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during user checking email exist", e);
        }
    }

    /**
     * Create {@link List} of users operation
     * @param set
     *        ResultSet object
     * @return
     *        user
     * @throws SQLException
     *        if a database access error occurs or this method is called on a closed result set
     */
    private User buildUser(ResultSet set) throws SQLException{

        User user = new User();
        if (set.next()) {
            user.setId(set.getInt(USER_ID));
            user.setLogin(set.getString(LOGIN));
            user.setName(set.getString(FIRST_NAME));
            user.setSurname(set.getString(LAST_NAME));
            user.setEmail(set.getString(EMAIL));
            Role role = Role.valueOf(set.getString(ROLE).toUpperCase());
            user.setRole(role);
            int mark = set.getInt(RATING);
            if (mark > ActionEvaluator.EXPERIENCED_MAX_SUM) {
                user.setStatus(Status.MASTER);
            } else if (mark > ActionEvaluator.BEGINNER_MAX_SUM) {
                user.setStatus(Status.EXPERIENCED);
            } else {
                user.setStatus(Status.BEGINNER);
            }
            user.setPicturePath(set.getString(PICTURE_PATH));

            String time = set.getString(BAN_DATE);
            if (time != null) {
                LocalDateTime date = set.getTimestamp(BAN_DATE).toLocalDateTime();
                user.setBanTime(date);
            }
        }
        return user;
    }
}
