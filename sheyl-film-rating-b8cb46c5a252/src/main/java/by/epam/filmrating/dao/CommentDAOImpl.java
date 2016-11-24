package by.epam.filmrating.dao;

import by.epam.filmrating.entity.Comment;
import by.epam.filmrating.entity.Film;
import by.epam.filmrating.entity.User;
import by.epam.filmrating.exception.DAOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code CommentDAOImpl} class is participant in Data Access Object Pattern.
 * This class is responsible to get {@link Comment} objects from database.
 * In this class all the inherited methods are implemented.
 * @author Dmitry Rafalovich
 *
 */
public class CommentDAOImpl extends CommentDAO {

    private static final String COMMENT_ID = "comment_id";
    private static final String USER_ID = "user_id";
    private static final String USER_PICTURE_PATH = "picturePath";
    private static final String TEXT = "text";
    private static final String DATE = "date";
    private static final String LOGIN = "login";
    private static final String TITLE = "title";

    private static final String SQL_CREATE_COMMENT = "INSERT INTO database1.comment (text, user_id, film_id) VALUES (?, ?, ?)";

    private static final String SQL_FIND_BY_ID = "SELECT comment.comment_id, comment.text, comment.date, comment.user_id " +
            "FROM database1.comment WHERE comment_id = ?";

    private static final String SQL_BAN_COMMENT_BY_ADMIN = "UPDATE database1.comment  SET text = 'CENSURED.' WHERE comment_id = ?";

    private static final String SQL_SELECT_COMMENTS_BY_FILM_ID = "SELECT comment.comment_id, comment.text, comment.date, user.login, user.picturePath FROM database1.comment" +
            " NATURAL JOIN user WHERE film_id = ?";

    private static final String SQL_SELECT_COMMENT_BY_USER_ID = "SELECT comment.comment_id, comment.text, comment.date, film.title FROM database1.comment" +
            " NATURAL JOIN film WHERE user_id = ?";

    private static final String SQL_SELECT_COMMENTS_BY_DATE = "SELECT comment.comment_id, comment.text, comment.date," +
            " user.login, user_id, film.title FROM database1.comment NATURAL JOIN user NATURAL JOIN film WHERE DATE(date) BETWEEN ? and ?";


    @Override
    public void create(Comment comment) throws DAOException {

        try (PreparedStatement statement = connector.prepareStatement(SQL_CREATE_COMMENT)) {
            statement.setString(1, comment.getText());
            statement.setLong(2, comment.getUser().getId());
            statement.setLong(3, comment.getFilm().getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during comment creating", e);
        }
    }

    @Override
    public Comment findById(long id) throws DAOException {
        try (PreparedStatement statement = connector.prepareStatement(SQL_FIND_BY_ID)) {

            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                Comment comment = new Comment();
                comment.setId(set.getLong(COMMENT_ID));
                comment.setText(set.getString(TEXT));
                LocalDateTime date = set.getTimestamp(DATE).toLocalDateTime();
                comment.setDate(date);
                User user = new User();
                long userId = set.getLong(USER_ID);
                user.setId(userId);
                comment.setUser(user);
                return comment;
            } else {
                throw new DAOException("Comment not found by this id.");
            }

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during comment creating", e);
        }
    }

    @Override
    public void banComment(long id) throws DAOException{

        try (PreparedStatement statement = connector.prepareStatement(SQL_BAN_COMMENT_BY_ADMIN)) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during comment banning", e);
        }
    }

    @Override
    public List<Comment> findCommentsByFilmId(long id) throws DAOException {

        List<Comment> comments = new ArrayList<>();
        try(PreparedStatement statement = connector.prepareStatement(SQL_SELECT_COMMENTS_BY_FILM_ID)) {
            statement.setLong(1,id);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                Comment comment = new Comment();
                comment.setId(set.getLong(COMMENT_ID));
                comment.setText(set.getString(TEXT));
                LocalDateTime date = set.getTimestamp(DATE).toLocalDateTime();
                comment.setDate(date);
                User user = new User();
                user.setLogin(set.getString(LOGIN));
                user.setPicturePath(set.getString(USER_PICTURE_PATH));
                comment.setUser(user);
                comments.add(comment);
            }
            return comments;

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during comments finding by filmId", e);
        }
    }

    @Override
    public List<Comment> findCommentsByUserId(long id) throws DAOException {

        List<Comment> comments = new ArrayList<>();
        try(PreparedStatement statement = connector.prepareStatement(SQL_SELECT_COMMENT_BY_USER_ID)) {
            statement.setLong(1,id);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                Comment comment = new Comment();
                comment.setId(set.getLong(COMMENT_ID));
                comment.setText(set.getString(TEXT));
                LocalDateTime date = set.getTimestamp(DATE).toLocalDateTime();
                comment.setDate(date);
                Film film = new Film();
                film.setTitle(set.getString(TITLE));
                comment.setFilm(film);
                comments.add(comment);
            }
            return comments;

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during comments finding by userId", e);
        }
    }

    @Override
    public List<Comment> findCommentsByDate(LocalDate date1, LocalDate date2) throws DAOException {

        List<Comment> comments = new ArrayList<>();
        try(PreparedStatement statement = connector.prepareStatement(SQL_SELECT_COMMENTS_BY_DATE)) {
            statement.setDate(1,Date.valueOf(date1));
            statement.setDate(2,Date.valueOf(date2));
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                Comment comment = new Comment();
                comment.setId(set.getLong(COMMENT_ID));
                comment.setText(set.getString(TEXT));
                LocalDateTime date = set.getTimestamp(DATE).toLocalDateTime();
                comment.setDate(date);
                Film film = new Film();
                film.setTitle(set.getString(TITLE));
                comment.setFilm(film);
                User user = new User();
                user.setId(set.getLong(USER_ID));
                user.setLogin(set.getString(LOGIN));
                comment.setUser(user);
                comments.add(comment);
            }
            return comments;

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during comments finding by date", e);
        }
    }


}
