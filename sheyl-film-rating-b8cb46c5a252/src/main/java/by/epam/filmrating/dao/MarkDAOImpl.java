package by.epam.filmrating.dao;

import by.epam.filmrating.entity.Mark;
import by.epam.filmrating.exception.DAOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * The {@code MarkDAOImpl} class is participant in Data Access Object Pattern.
 * This class is responsible to get {@link Mark} objects from database.
 * In this class all the inherited methods are implemented.
 * @author Dmitry Rafalovich
 *
 */
public class MarkDAOImpl extends MarkDAO {

    private static final String AVG = "AVG(mark)";
    private static final String COUNT = "COUNT(mark)";

    private static final String SQL_CREATE_MARK = "INSERT INTO database1.rating (film_id, user_id, mark) VALUES (?, ?, ?)";
    private static final String SQL_SELECT_RATING_BY_FILM_ID = "SELECT AVG(mark), COUNT(mark) FROM database1.rating " +
            "WHERE film_id = ?";
    private static final String SQL_SELECT_COUNT_BY_USER_ID = "SELECT COUNT(mark) FROM database1.rating " +
            "WHERE user_id = ?";

    @Override
    public void create(Mark mark) throws DAOException {
        try (PreparedStatement statement = connector.prepareStatement(SQL_CREATE_MARK)) {
            statement.setLong(1, mark.getFilm().getId());
            statement.setLong(2, mark.getUser().getId());
            statement.setInt(3, mark.getMark());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during mark creating", e);
        }
    }

    @Override
    public Number[] findRatingByFilmId(long id) throws DAOException {

        Number[] ratingData = new Number[2];
        try (PreparedStatement statement = connector.prepareStatement(SQL_SELECT_RATING_BY_FILM_ID)) {
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            if (set.next()) {

                int count = set.getInt(COUNT);
                ratingData[0] = count;
                if (count > 0) {
                    ratingData[1] = set.getDouble(AVG);
                } else {
                    ratingData[1] = 0d;
                }
            }
            return ratingData;

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during rating finding by filmId", e);
        }
    }

    @Override
    public  int findMarksCountByUserId(long id) throws DAOException {

        int count = 0;
        try (PreparedStatement statement = connector.prepareStatement(SQL_SELECT_COUNT_BY_USER_ID)) {
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                count = set.getInt(COUNT);
            }
            return count;

        } catch (SQLException e) {
            throw new DAOException("Invalid database operation during mark's count finding by userId", e);
        }
    }
}
